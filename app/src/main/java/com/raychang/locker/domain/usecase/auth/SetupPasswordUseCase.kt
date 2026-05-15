package com.raychang.locker.domain.usecase.auth

import com.raychang.locker.domain.repository.AuthRepository
import javax.inject.Inject

class SetupPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(password: String) {
        authRepository.setupPassword(password)
    }
}
