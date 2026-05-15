package com.raychang.locker.domain.usecase.auth

import com.raychang.locker.domain.repository.AuthRepository
import javax.inject.Inject

class ValidateSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    sealed class Result {
        data object Valid : Result()
        data object Expired : Result()
        data object NoPassword : Result()
    }

    suspend operator fun invoke(): Result {
        if (!authRepository.hasMasterPassword()) return Result.NoPassword
        return if (authRepository.isSessionValid()) {
            authRepository.refreshSession()
            Result.Valid
        } else {
            Result.Expired
        }
    }
}
