package com.raychang.locker.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.R
import com.raychang.locker.domain.repository.AuthRepository
import com.raychang.locker.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val biometric = authRepository.isBiometricEnabled()
            _uiState.update { it.copy(biometricEnabled = biometric) }
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorResId = null) }
    }

    fun onLogin() {
        val password = _uiState.value.password
        if (password.isBlank()) {
            _uiState.update { it.copy(errorResId = R.string.error_password_empty) }
            return
        }
        viewModelScope.launch {
            val success = loginUseCase(password)
            if (success) {
                _uiState.update { it.copy(isLoggedIn = true) }
            } else {
                _uiState.update { it.copy(errorResId = R.string.error_incorrect_password) }
            }
        }
    }

    fun onBiometricSuccess() {
        viewModelScope.launch {
            authRepository.refreshSession()
            _uiState.update { it.copy(isLoggedIn = true, showBiometricPrompt = false) }
        }
    }

    fun onBiometricFailure() {
        _uiState.update { it.copy(showBiometricPrompt = false) }
    }

    fun resetApp() {
        viewModelScope.launch {
            authRepository.resetAll()
            _uiState.update { it.copy(isReset = true) }
        }
    }
}
