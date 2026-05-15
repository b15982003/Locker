package com.raychang.locker.domain.repository

import com.raychang.locker.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllTags(): Flow<List<Tag>>
    suspend fun getAllTagsList(): List<Tag>
    suspend fun getTagById(id: Long): Tag?
    suspend fun insert(tag: Tag): Long
    suspend fun update(tag: Tag)
    suspend fun delete(tag: Tag)
    suspend fun deleteAll()
    suspend fun getNextOrder(): Int
}
