package com.raychang.locker.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raychang.locker.data.local.dao.CredentialDao
import com.raychang.locker.data.local.dao.TagDao
import com.raychang.locker.data.local.entity.CredentialEntity
import com.raychang.locker.data.local.entity.TagEntity
import com.raychang.locker.domain.repository.BackupRestoreRepository
import javax.inject.Inject

data class BackupData(
    val tags: List<TagEntity>,
    val credentials: List<CredentialEntity>
)

class BackupRestoreRepositoryImpl @Inject constructor(
    private val tagDao: TagDao,
    private val credentialDao: CredentialDao
) : BackupRestoreRepository {

    private val gson = Gson()

    override suspend fun exportToJson(): String {
        val tags = tagDao.getAllTagsList()
        val credentials = credentialDao.getAllCredentialsList()
        return gson.toJson(BackupData(tags, credentials))
    }

    override suspend fun importFromJson(json: String) {
        val type = object : TypeToken<BackupData>() {}.type
        val data: BackupData = gson.fromJson(json, type)

        // Clear existing data
        credentialDao.deleteAll()
        tagDao.deleteAll()

        // Insert backup data
        data.tags.forEach { tagDao.insert(it) }
        data.credentials.forEach { credentialDao.insert(it) }
    }
}
