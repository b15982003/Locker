package com.raychang.locker.domain.repository

import com.raychang.locker.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {
    fun getAllCredentials(): Flow<List<Credential>>
    suspend fun getAllCredentialsList(): List<Credential>
    suspend fun getCredentialById(id: Long): Credential?
    fun getCredentialByIdFlow(id: Long): Flow<Credential?>
    suspend fun insert(credential: Credential): Long
    suspend fun update(credential: Credential)
    suspend fun delete(credential: Credential)
    suspend fun deleteAll()
}
