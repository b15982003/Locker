package com.raychang.locker.domain.usecase.tag

import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.repository.TagRepository
import javax.inject.Inject

class ReorderTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(reorderedTags: List<Tag>) {
        reorderedTags.forEachIndexed { index, tag ->
            tagRepository.update(tag.copy(order = index))
        }
    }
}
