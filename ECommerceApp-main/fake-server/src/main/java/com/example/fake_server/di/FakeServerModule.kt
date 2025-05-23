package com.example.fake_server.di

import android.app.Application
import com.example.fake_server.FakeHttpClient
import com.example.fake_server.FakeServer
import com.example.fake_server.db.CategoryDao
import com.example.fake_server.db.FakeServerDatabase
import com.example.fake_server.db.ProductDao
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
    fun provideFakeServerDatabase(application: Application): FakeServerDatabase {
        return FakeServerDatabase.getInstance(application)
    }

    @Provides
    fun provideCategoryDao(database: FakeServerDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideProductDao(database: FakeServerDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideUserDao(database: FakeServerDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideFakeServer(userDao: UserDao, categoryDao: CategoryDao, productDao: ProductDao): FakeServer {
        return FakeServer(userDao, categoryDao, productDao)
    }

    @Provides
    @Singleton
    fun provideFakeHttpClient(fakeServer: FakeServer): FakeHttpClient {
        return FakeHttpClient(fakeServer)
    }

}