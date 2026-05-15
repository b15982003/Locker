package com.raychang.locker.presentation.credential.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.R
import com.raychang.locker.domain.model.Credential
import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.repository.CredentialRepository
import com.raychang.locker.domain.usecase.credential.CreateCredentialUseCase
import com.raychang.locker.domain.usecase.credential.UpdateCredentialUseCase
import com.raychang.locker.domain.usecase.tag.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CredentialEditViewModel @Inject constructor(
    private val credentialRepository: CredentialRepository,
    private val createCredentialUseCase: CreateCredentialUseCase,
    private val updateCredentialUseCase: UpdateCredentialUseCase,
    getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CredentialEditUiState())
    val uiState: StateFlow<CredentialEditUiState> = _uiState.asStateFlow()

    val tags: StateFlow<List<Tag>> = getTagsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var existingCredential: Credential? = null

    fun loadCredential(id: Long) {
        viewModelScope.launch {
            val credential = credentialRepository.getCredentialById(id) ?: return@launch
            existingCredential = credential
            _uiState.update {
                it.copy(
                    title = credential.title,
                    account = credential.account,
                    password = credential.password,
                    description = credential.description,
                    selectedTagId = credential.tagId,
                    isEditMode = true
                )
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title, errors = it.errors - "title") }
    }

    fun onAccountChange(account: String) {
        _uiState.update { it.copy(account = account, errors = it.errors - "account") }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errors = it.errors - "password") }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onTagSelected(tagId: Long?) {
        _uiState.update { it.copy(selectedTagId = tagId) }
    }

    fun onSave() {
        val state = _uiState.value
        val errors = mutableMapOf<String, Int>()
        if (state.title.isBlank()) errors["title"] = R.string.error_title_required
        if (state.account.isBlank()) errors["account"] = R.string.error_account_required
        if (state.password.isBlank()) errors["password"] = R.string.error_password_required

        if (errors.isNotEmpty()) {
            _uiState.update { it.copy(errors = errors) }
            return
        }

        viewModelScope.launch {
            if (state.isEditMode && existingCredential != null) {
                updateCredentialUseCase(
                    existingCredential!!.copy(
                        title = state.title,
                        account = state.account,
                        password = state.password,
                        description = state.description,
                        tagId = state.selectedTagId
                    )
                )
            } else {
                createCredentialUseCase(
                    Credential(
                        title = state.title,
                        account = state.account,
                        password = state.password,
                        description = state.description,
                        tagId = state.selectedTagId
                    )
                )
            }
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}
