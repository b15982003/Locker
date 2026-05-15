package com.raychang.locker.domain.repository

interface BackupRestoreRepository {
    suspend fun exportToJson(): String
    suspend fun importFromJson(json: String)
}
