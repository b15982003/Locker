package com.raychang.locker.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.domain.usecase.auth.ValidateSessionUseCase
import com.raychang.locker.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val validateSessionUseCase: ValidateSessionUseCase
) : ViewModel() {

    private val _destination = MutableStateFlow<String?>(null)
    val destination: StateFlow<String?> = _destination.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500L)
            _destination.value = when (validateSessionUseCase()) {
                ValidateSessionUseCase.Result.NoPassword -> Routes.SETUP_PASSWORD
                ValidateSessionUseCase.Result.Expired -> Routes.LOGIN
                ValidateSessionUseCase.Result.Valid -> Routes.MAIN
            }
        }
    }
}
