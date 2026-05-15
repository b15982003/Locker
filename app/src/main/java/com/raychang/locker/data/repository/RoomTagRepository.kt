package com.raychang.locker.data.repository

import com.raychang.locker.data.local.dao.TagDao
import com.raychang.locker.data.local.entity.TagEntity
import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomTagRepository @Inject constructor(
    private val tagDao: TagDao
) : TagRepository {

    override fun getAllTags(): Flow<List<Tag>> =
        tagDao.getAllTags().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getAllTagsList(): List<Tag> =
        tagDao.getAllTagsList().map { it.toDomain() }

    override suspend fun getTagById(id: Long): Tag? =
        tagDao.getTagById(id)?.toDomain()

    override suspend fun insert(tag: Tag): Long =
        tagDao.insert(tag.toEntity())

    override suspend fun update(tag: Tag) =
        tagDao.update(tag.toEntity())

    override suspend fun delete(tag: Tag) =
        tagDao.delete(tag.toEntity())

    override suspend fun deleteAll() =
        tagDao.deleteAll()

    override suspend fun getNextOrder(): Int =
        tagDao.getNextOrder()
}

private fun TagEntity.toDomain() = Tag(id = id, name = name, order = order)
private fun Tag.toEntity() = TagEntity(id = id, name = name, order = order)
