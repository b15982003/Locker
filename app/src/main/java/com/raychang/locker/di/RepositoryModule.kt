package com.raychang.locker.di

import com.raychang.locker.data.repository.AuthRepositoryImpl
import com.raychang.locker.data.repository.BackupRestoreRepositoryImpl
import com.raychang.locker.data.repository.RoomCredentialRepository
import com.raychang.locker.data.repository.RoomTagRepository
import com.raychang.locker.domain.repository.AuthRepository
import com.raychang.locker.domain.repository.BackupRestoreRepository
import com.raychang.locker.domain.repository.CredentialRepository
import com.raychang.locker.domain.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTagRepository(impl: RoomTagRepository): TagRepository

    @Binds
    @Singleton
    abstract fun bindCredentialRepository(impl: RoomCredentialRepository): CredentialRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindBackupRestoreRepository(impl: BackupRestoreRepositoryImpl): BackupRestoreRepository
}
