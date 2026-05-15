package com.raychang.locker.data.repository

import android.util.Base64
import com.raychang.locker.data.local.dao.CredentialDao
import com.raychang.locker.data.local.dao.TagDao
import com.raychang.locker.data.preferences.PreferencesManager
import com.raychang.locker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val credentialDao: CredentialDao,
    private val tagDao: TagDao
) : AuthRepository {

    companion object {
        private const val ITERATIONS = 65536
        private const val KEY_LENGTH = 256
        private const val SALT_LENGTH = 16
        private const val SESSION_TIMEOUT_MS = 5 * 60 * 1000L // 5 minutes
    }

    override suspend fun hasMasterPassword(): Boolean =
        preferencesManager.getMasterPasswordHash() != null

    override suspend fun setupPassword(password: String) {
        val salt = generateSalt()
        val hash = hashPassword(password, salt)
        preferencesManager.setMasterPassword(hash, Base64.encodeToString(salt, Base64.NO_WRAP))
        preferencesManager.setLastActiveTimestamp(System.currentTimeMillis())
    }

    override suspend fun verifyPassword(password: String): Boolean {
        val storedHash = preferencesManager.getMasterPasswordHash() ?: return false
        val saltString = preferencesManager.getPasswordSalt() ?: return false
        val salt = Base64.decode(saltString, Base64.NO_WRAP)
        val hash = hashPassword(password, salt)
        return hash == storedHash
    }

    override suspend fun changePassword(currentPassword: String, newPassword: String): Boolean {
        if (!verifyPassword(currentPassword)) return false
        val salt = generateSalt()
        val hash = hashPassword(newPassword, salt)
        preferencesManager.setMasterPassword(hash, Base64.encodeToString(salt, Base64.NO_WRAP))
        return true
    }

    override suspend fun isSessionValid(): Boolean {
        val lastActive = preferencesManager.getLastActiveTimestamp() ?: return false
        return System.currentTimeMillis() - lastActive <= SESSION_TIMEOUT_MS
    }

    override suspend fun refreshSession() {
        preferencesManager.setLastActiveTimestamp(System.currentTimeMillis())
    }

    override suspend fun clearSession() {
        preferencesManager.clearLastActiveTimestamp()
    }

    override val biometricEnabled: Flow<Boolean> = preferencesManager.biometricEnabled

    override suspend fun isBiometricEnabled(): Boolean =
        preferencesManager.isBiometricEnabled()

    override suspend fun setBiometricEnabled(enabled: Boolean) =
        preferencesManager.setBiometricEnabled(enabled)

    override suspend fun resetAll() {
        credentialDao.deleteAll()
        tagDao.deleteAll()
        preferencesManager.clearAll()
    }

    private fun generateSalt(): ByteArray {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)
        return salt
    }

    private fun hashPassword(password: String, salt: ByteArray): String {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded
        return Base64.encodeToString(hash, Base64.NO_WRAP)
    }
}
