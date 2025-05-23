package com.example.fake_server.di

import android.app.Application
import android.content.Context
import com.example.fake_server.FakeHttpClient
import com.example.fake_server.FakeServer
import com.example.fake_server.db.FakeServerDatabase
import com.example.fake_server.db.PostDao
import com.example.fake_server.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FakeServerModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideFakeServerDatabase(application: Application): FakeServerDatabase {
        return FakeServerDatabase.getInstance(application)
    }

    @Provides
    fun provideProductDao(database: FakeServerDatabase): PostDao {
        return database.postDao()
    }

    @Provides
    fun provideUserDao(database: FakeServerDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideFakeServer(context: Context, userDao: UserDao, postDao: PostDao): FakeServer {
        return FakeServer(context, userDao, postDao)
    }

    @Provides
    @Singleton
    fun provideFakeHttpClient(fakeServer: FakeServer): FakeHttpClient {
        return FakeHttpClient(fakeServer)
    }

}