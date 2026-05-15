package com.raychang.locker.domain.model

data class Credential(
    val id: Long = 0,
    val tagId: Long? = null,
    val title: String,
    val account: String,
    val password: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
