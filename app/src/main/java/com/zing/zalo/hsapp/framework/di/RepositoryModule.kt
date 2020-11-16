package com.zing.zalo.hsapp.framework.di

import com.zing.zalo.data.repository.Repository
import com.zing.zalo.hsapp.framework.repository.InMemoryRepositoryImpl
import com.zing.zalo.hsapp.framework.repository.LocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Provide the repository
 * (Repository class is in data module, thus injecting it via @Inject causes the module depends on Android Hilt)
 */
@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        localRepository: LocalRepositoryImpl,
        inMemoryRepository: InMemoryRepositoryImpl
    ): Repository {
        return Repository(localRepository, inMemoryRepository)
    }
}