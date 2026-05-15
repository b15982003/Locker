package com.raychang.locker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raychang.locker.data.local.dao.CredentialDao
import com.raychang.locker.data.local.dao.TagDao
import com.raychang.locker.data.local.entity.CredentialEntity
import com.raychang.locker.data.local.entity.TagEntity

@Database(
    entities = [TagEntity::class, CredentialEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LockerDatabase : RoomDatabase() {
    abstract fun tagDao(): TagDao
    abstract fun credentialDao(): CredentialDao
}
