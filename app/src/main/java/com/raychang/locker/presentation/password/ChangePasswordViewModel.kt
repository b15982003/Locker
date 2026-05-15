package com.raychang.locker.presentation.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.R
import com.raychang.locker.domain.usecase.auth.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordUiState())
    val uiState: StateFlow<ChangePasswordUiState> = _uiState.asStateFlow()

    fun onCurrentPasswordChange(value: String) {
        _uiState.update { it.copy(currentPassword = value, errorResId = null) }
    }

    fun onNewPasswordChange(value: String) {
        _uiState.update { it.copy(newPassword = value, errorResId = null) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value, errorResId = null) }
    }

    fun onSubmit() {
        val state = _uiState.value
        when {
            state.currentPassword.isBlank() -> {
                _uiState.update { it.copy(errorResId = R.string.error_current_password_required) }
            }
            state.newPassword.isBlank() -> {
                _uiState.update { it.copy(errorResId = R.string.error_new_password_required) }
            }
            state.newPassword.length < 4 -> {
                _uiState.update { it.copy(errorResId = R.string.error_new_password_too_short) }
            }
            state.newPassword != state.confirmPassword -> {
                _uiState.update { it.copy(errorResId = R.string.error_new_passwords_mismatch) }
            }
            else -> {
                viewModelScope.launch {
                    val success = changePasswordUseCase(state.currentPassword, state.newPassword)
                    if (success) {
                        _uiState.update { it.copy(success = true) }
                    } else {
                        _uiState.update { it.copy(errorResId = R.string.error_current_password_incorrect) }
                    }
                }
            }
        }
    }
}
