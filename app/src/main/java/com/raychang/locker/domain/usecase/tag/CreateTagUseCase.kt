package com.raychang.locker.domain.usecase.tag

import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.repository.TagRepository
import javax.inject.Inject

class CreateTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(name: String): Long {
        val order = tagRepository.getNextOrder()
        return tagRepository.insert(Tag(name = name, order = order))
    }
}
