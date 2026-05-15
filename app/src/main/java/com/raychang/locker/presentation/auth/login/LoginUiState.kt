package com.raychang.locker.presentation.auth.login

import androidx.annotation.StringRes

data class LoginUiState(
    val password: String = "",
    @StringRes val errorResId: Int? = null,
    val isLoggedIn: Boolean = false,
    val isReset: Boolean = false,
    val biometricEnabled: Boolean = false,
    val showBiometricPrompt: Boolean = false
)
