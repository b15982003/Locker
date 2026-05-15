package com.raychang.locker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.raychang.locker.data.local.entity.CredentialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialDao {

    @Query("SELECT * FROM credentials ORDER BY tagId ASC, title ASC")
    fun getAllCredentials(): Flow<List<CredentialEntity>>

    @Query("SELECT * FROM credentials ORDER BY tagId ASC, title ASC")
    suspend fun getAllCredentialsList(): List<CredentialEntity>

    @Query("SELECT * FROM credentials WHERE id = :id")
    suspend fun getCredentialById(id: Long): CredentialEntity?

    @Query("SELECT * FROM credentials WHERE id = :id")
    fun getCredentialByIdFlow(id: Long): Flow<CredentialEntity?>

    @Insert
    suspend fun insert(credential: CredentialEntity): Long

    @Update
    suspend fun update(credential: CredentialEntity)

    @Delete
    suspend fun delete(credential: CredentialEntity)

    @Query("DELETE FROM credentials")
    suspend fun deleteAll()
}
