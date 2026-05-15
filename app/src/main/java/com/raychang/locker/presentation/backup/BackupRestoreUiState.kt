package com.raychang.locker.presentation.backup

import android.content.Intent

data class BackupRestoreUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
    val emailIntent: Intent? = null
)
