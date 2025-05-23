package com.example.post_creation.di

import com.example.core.model.ApiService
import com.example.post_creation.model.PostCreationRepository
import com.example.post_creation.model.PostCreationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PostCreationModule {

    @Provides
    @Singleton
    fun providePostCreationRepository(apiService: ApiService): PostCreationRepository {
        return PostCreationRepositoryImpl(apiService)
    }
}