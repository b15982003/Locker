package com.raychang.locker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.domain.usecase.credential.GetCredentialsByTagUseCase
import com.raychang.locker.domain.usecase.credential.TaggedCredentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCredentialsByTagUseCase: GetCredentialsByTagUseCase
) : ViewModel() {

    val taggedCredentials: StateFlow<List<TaggedCredentials>> =
        getCredentialsByTagUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _expandAll = MutableStateFlow(false)
    val expandAll: StateFlow<Boolean> = _expandAll.asStateFlow()

    private val _showAllPasswords = MutableStateFlow(false)
    val showAllPasswords: StateFlow<Boolean> = _showAllPasswords.asStateFlow()

    fun toggleExpandAll() {
        _expandAll.value = !_expandAll.value
        if (!_expandAll.value) {
            _showAllPasswords.value = false
        }
    }

    fun toggleShowAllPasswords() {
        _showAllPasswords.value = !_showAllPasswords.value
    }
}
