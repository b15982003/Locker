package com.raychang.locker.presentation.password

import androidx.annotation.StringRes

data class ChangePasswordUiState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    @StringRes val errorResId: Int? = null,
    val success: Boolean = false
)
