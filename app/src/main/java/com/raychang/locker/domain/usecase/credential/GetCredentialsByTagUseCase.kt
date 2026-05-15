package com.raychang.locker.domain.usecase.credential

import com.raychang.locker.domain.model.Credential
import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.repository.CredentialRepository
import com.raychang.locker.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class TaggedCredentials(
    val tag: Tag?,
    val credentials: List<Credential>
)

class GetCredentialsByTagUseCase @Inject constructor(
    private val credentialRepository: CredentialRepository,
    private val tagRepository: TagRepository
) {
    operator fun invoke(): Flow<List<TaggedCredentials>> {
        return combine(
            tagRepository.getAllTags(),
            credentialRepository.getAllCredentials()
        ) { tags, credentials ->
            val grouped = credentials.groupBy { it.tagId }
            val result = mutableListOf<TaggedCredentials>()

            for (tag in tags) {
                val tagCredentials = grouped[tag.id].orEmpty()
                if (tagCredentials.isNotEmpty()) {
                    result.add(TaggedCredentials(tag, tagCredentials))
                }
            }

            val untagged = grouped[null].orEmpty()
            if (untagged.isNotEmpty()) {
                result.add(TaggedCredentials(null, untagged))
            }

            result
        }
    }
}
