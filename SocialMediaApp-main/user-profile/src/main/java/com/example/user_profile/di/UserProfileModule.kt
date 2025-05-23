package com.example.user_profile.di

import com.example.authentication.model.AuthDataStore
import com.example.user_profile.model.ProfileRepository
import com.example.user_profile.model.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserProfileModule {

    @Provides
    @Singleton
    fun provideProfileRepository(authDataStore: AuthDataStore): ProfileRepository {
        return ProfileRepositoryImpl(authDataStore)
    }

}