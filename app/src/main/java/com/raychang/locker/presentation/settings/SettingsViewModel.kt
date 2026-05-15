package com.raychang.locker.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val biometricEnabled: StateFlow<Boolean> = authRepository.biometricEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _loggedOut = MutableStateFlow(false)
    val loggedOut: StateFlow<Boolean> = _loggedOut.asStateFlow()

    fun setBiometricEnabled(enabled: Boolean) {
        viewModelScope.launch {
            authRepository.setBiometricEnabled(enabled)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.clearSession()
            _loggedOut.value = true
        }
    }
}
