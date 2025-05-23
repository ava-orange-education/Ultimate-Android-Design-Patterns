package com.example.feed.di

import com.example.core.model.ApiRepository
import com.example.feed.model.FeedRepository
import com.example.feed.model.FeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeedModule {

    @Provides
    @Singleton
    fun provideFeedRepository(apiRepository: ApiRepository): FeedRepository {
        return FeedRepositoryImpl(apiRepository)
    }
}