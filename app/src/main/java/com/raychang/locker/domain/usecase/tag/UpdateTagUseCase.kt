package com.raychang.locker.domain.usecase.tag

import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.repository.TagRepository
import javax.inject.Inject

class UpdateTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(tag: Tag) {
        tagRepository.update(tag)
    }
}
