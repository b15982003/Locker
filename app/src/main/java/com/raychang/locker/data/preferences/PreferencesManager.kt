package com.raychang.locker.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "locker_prefs")

@Singleton
class PreferencesManager @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore

    private object Keys {
        val MASTER_PASSWORD_HASH = stringPreferencesKey("master_password_hash")
        val PASSWORD_SALT = stringPreferencesKey("password_salt")
        val LAST_ACTIVE_TIMESTAMP = longPreferencesKey("last_active_timestamp")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
    }

    val biometricEnabled: Flow<Boolean> = dataStore.data.map { it[Keys.BIOMETRIC_ENABLED] ?: false }

    suspend fun getMasterPasswordHash(): String? = dataStore.data.first()[Keys.MASTER_PASSWORD_HASH]
    suspend fun getPasswordSalt(): String? = dataStore.data.first()[Keys.PASSWORD_SALT]
    suspend fun getLastActiveTimestamp(): Long? = dataStore.data.first()[Keys.LAST_ACTIVE_TIMESTAMP]
    suspend fun isBiometricEnabled(): Boolean = dataStore.data.first()[Keys.BIOMETRIC_ENABLED] ?: false

    suspend fun setMasterPassword(hash: String, salt: String) {
        dataStore.edit {
            it[Keys.MASTER_PASSWORD_HASH] = hash
            it[Keys.PASSWORD_SALT] = salt
        }
    }

    suspend fun setLastActiveTimestamp(timestamp: Long) {
        dataStore.edit { it[Keys.LAST_ACTIVE_TIMESTAMP] = timestamp }
    }

    suspend fun clearLastActiveTimestamp() {
        dataStore.edit { it.remove(Keys.LAST_ACTIVE_TIMESTAMP) }
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        dataStore.edit { it[Keys.BIOMETRIC_ENABLED] = enabled }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}
