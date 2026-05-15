package com.raychang.locker.presentation.credential.detail

import com.raychang.locker.domain.model.Credential

data class CredentialDetailUiState(
    val credential: Credential? = null,
    val tagName: String? = null,
    val passwordVisible: Boolean = false,
    val isDeleted: Boolean = false
)
