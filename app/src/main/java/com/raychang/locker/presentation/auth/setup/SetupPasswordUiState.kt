package com.raychang.locker.presentation.auth.setup

import androidx.annotation.StringRes

data class SetupPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    @StringRes val errorResId: Int? = null,
    val isComplete: Boolean = false
)
