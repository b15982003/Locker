package com.raychang.locker.domain.usecase.credential

import com.raychang.locker.domain.model.Credential
import com.raychang.locker.domain.repository.CredentialRepository
import javax.inject.Inject

class DeleteCredentialUseCase @Inject constructor(
    private val credentialRepository: CredentialRepository
) {
    suspend operator fun invoke(credential: Credential) {
        credentialRepository.delete(credential)
    }
}
