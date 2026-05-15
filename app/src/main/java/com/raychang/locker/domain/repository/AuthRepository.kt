package com.raychang.locker.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun hasMasterPassword(): Boolean
    suspend fun setupPassword(password: String)
    suspend fun verifyPassword(password: String): Boolean
    suspend fun changePassword(currentPassword: String, newPassword: String): Boolean
    suspend fun isSessionValid(): Boolean
    suspend fun refreshSession()
    suspend fun clearSession()
    val biometricEnabled: Flow<Boolean>
    suspend fun isBiometricEnabled(): Boolean
    suspend fun setBiometricEnabled(enabled: Boolean)
    suspend fun resetAll()
}
