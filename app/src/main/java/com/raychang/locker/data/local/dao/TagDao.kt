package com.raychang.locker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.raychang.locker.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tags ORDER BY `order` ASC")
    fun getAllTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags ORDER BY `order` ASC")
    suspend fun getAllTagsList(): List<TagEntity>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getTagById(id: Long): TagEntity?

    @Insert
    suspend fun insert(tag: TagEntity): Long

    @Update
    suspend fun update(tag: TagEntity)

    @Delete
    suspend fun delete(tag: TagEntity)

    @Query("DELETE FROM tags")
    suspend fun deleteAll()

    @Query("SELECT COALESCE(MAX(`order`), -1) + 1 FROM tags")
    suspend fun getNextOrder(): Int
}
