package com.example.authentication.di

import android.content.Context
import com.example.authentication.model.AuthDataStore
import com.example.authentication.model.AuthRepositoryImpl
import com.example.core.model.ApiService
import com.example.core.model.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService, authDataStore: AuthDataStore): AuthRepository {
        return AuthRepositoryImpl(apiService, authDataStore)
    }

    @Provides
    @Singleton
    fun provideAuthDataStore(@ApplicationContext context: Context): AuthDataStore {
        return AuthDataStore(context)
    }

}