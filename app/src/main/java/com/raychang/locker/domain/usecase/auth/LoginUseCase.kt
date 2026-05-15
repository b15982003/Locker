package com.raychang.locker.domain.usecase.auth

import com.raychang.locker.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(password: String): Boolean {
        val valid = authRepository.verifyPassword(password)
        if (valid) {
            authRepository.refreshSession()
        }
        return valid
    }
}
