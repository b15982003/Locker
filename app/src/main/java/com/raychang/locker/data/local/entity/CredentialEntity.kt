package com.raychang.locker.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "credentials",
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("tagId")]
)
data class CredentialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tagId: Long? = null,
    val title: String,
    val account: String,
    val password: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
