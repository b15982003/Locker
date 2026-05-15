package com.raychang.locker.data.repository

import com.raychang.locker.data.local.dao.CredentialDao
import com.raychang.locker.data.local.entity.CredentialEntity
import com.raychang.locker.domain.model.Credential
import com.raychang.locker.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomCredentialRepository @Inject constructor(
    private val credentialDao: CredentialDao
) : CredentialRepository {

    override fun getAllCredentials(): Flow<List<Credential>> =
        credentialDao.getAllCredentials().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getAllCredentialsList(): List<Credential> =
        credentialDao.getAllCredentialsList().map { it.toDomain() }

    override suspend fun getCredentialById(id: Long): Credential? =
        credentialDao.getCredentialById(id)?.toDomain()

    override fun getCredentialByIdFlow(id: Long): Flow<Credential?> =
        credentialDao.getCredentialByIdFlow(id).map { it?.toDomain() }

    override suspend fun insert(credential: Credential): Long =
        credentialDao.insert(credential.toEntity())

    override suspend fun update(credential: Credential) =
        credentialDao.update(credential.toEntity())

    override suspend fun delete(credential: Credential) =
        credentialDao.delete(credential.toEntity())

    override suspend fun deleteAll() =
        credentialDao.deleteAll()
}

private fun CredentialEntity.toDomain() = Credential(
    id = id,
    tagId = tagId,
    title = title,
    account = account,
    password = password,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

private fun Credential.toEntity() = CredentialEntity(
    id = id,
    tagId = tagId,
    title = title,
    account = account,
    password = password,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)
