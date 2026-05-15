package com.raychang.locker.presentation.credential.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.domain.repository.CredentialRepository
import com.raychang.locker.domain.repository.TagRepository
import com.raychang.locker.domain.usecase.credential.DeleteCredentialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CredentialDetailViewModel @Inject constructor(
    private val credentialRepository: CredentialRepository,
    private val tagRepository: TagRepository,
    private val deleteCredentialUseCase: DeleteCredentialUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CredentialDetailUiState())
    val uiState: StateFlow<CredentialDetailUiState> = _uiState.asStateFlow()

    fun loadCredential(id: Long) {
        viewModelScope.launch {
            val credential = credentialRepository.getCredentialById(id) ?: return@launch
            val tagName = credential.tagId?.let { tagRepository.getTagById(it)?.name }
            _uiState.update { it.copy(credential = credential, tagName = tagName) }
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun deleteCredential() {
        val credential = _uiState.value.credential ?: return
        viewModelScope.launch {
            deleteCredentialUseCase(credential)
            _uiState.update { it.copy(isDeleted = true) }
        }
    }
}
