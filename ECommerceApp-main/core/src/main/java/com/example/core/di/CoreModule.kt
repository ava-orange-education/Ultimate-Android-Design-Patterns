package com.example.core.di

import com.example.core.ApiService
import com.example.fake_server.FakeHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideApiService(fakeHttpClient: FakeHttpClient): ApiService {
          return Retrofit.Builder()
            .baseUrl("https://example.com") // Not relevant for FakeServer
            .callFactory(fakeHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    }

}