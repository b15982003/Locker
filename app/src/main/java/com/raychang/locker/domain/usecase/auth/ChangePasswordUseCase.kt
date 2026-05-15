package com.raychang.locker.domain.usecase.auth

import com.raychang.locker.domain.repository.AuthRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(currentPassword: String, newPassword: String): Boolean {
        return authRepository.changePassword(currentPassword, newPassword)
    }
}
