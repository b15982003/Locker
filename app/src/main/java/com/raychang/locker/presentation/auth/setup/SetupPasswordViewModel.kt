package com.raychang.locker.presentation.auth.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.R
import com.raychang.locker.domain.usecase.auth.SetupPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupPasswordViewModel @Inject constructor(
    private val setupPasswordUseCase: SetupPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SetupPasswordUiState())
    val uiState: StateFlow<SetupPasswordUiState> = _uiState.asStateFlow()

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorResId = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, errorResId = null) }
    }

    fun onSubmit() {
        val state = _uiState.value
        when {
            state.password.isBlank() -> {
                _uiState.update { it.copy(errorResId = R.string.error_password_empty) }
            }
            state.password.length < 4 -> {
                _uiState.update { it.copy(errorResId = R.string.error_password_too_short) }
            }
            state.password != state.confirmPassword -> {
                _uiState.update { it.copy(errorResId = R.string.error_passwords_mismatch) }
            }
            else -> {
                viewModelScope.launch {
                    setupPasswordUseCase(state.password)
                    _uiState.update { it.copy(isComplete = true) }
                }
            }
        }
    }
}
