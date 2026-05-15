package com.raychang.locker.presentation.credential.edit

data class CredentialEditUiState(
    val title: String = "",
    val account: String = "",
    val password: String = "",
    val description: String = "",
    val selectedTagId: Long? = null,
    val isEditMode: Boolean = false,
    val isSaved: Boolean = false,
    val errors: Map<String, Int> = emptyMap()
)
