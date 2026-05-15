package com.raychang.locker.di

import android.content.Context
import androidx.room.Room
import com.raychang.locker.data.local.LockerDatabase
import com.raychang.locker.data.local.dao.CredentialDao
import com.raychang.locker.data.local.dao.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LockerDatabase {
        return Room.databaseBuilder(
            context,
            LockerDatabase::class.java,
            "locker_database"
        ).build()
    }

    @Provides
    fun provideTagDao(database: LockerDatabase): TagDao = database.tagDao()

    @Provides
    fun provideCredentialDao(database: LockerDatabase): CredentialDao = database.credentialDao()
}
