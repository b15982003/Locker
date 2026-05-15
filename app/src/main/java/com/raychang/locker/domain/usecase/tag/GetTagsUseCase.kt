package com.raychang.locker.domain.usecase.tag

import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    operator fun invoke(): Flow<List<Tag>> = tagRepository.getAllTags()
}
