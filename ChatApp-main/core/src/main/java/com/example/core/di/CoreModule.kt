package com.example.core.di

import android.app.Application
import com.example.core.db.ChatDatabase
import com.example.core.db.ContactDao
import com.example.core.db.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ChatDatabase {
        return ChatDatabase.getInstance(application)
    }

    @Provides
    fun provideProductDao(database: ChatDatabase): ContactDao {
        return database.contactDao()
    }

    @Provides
    fun provideUserDao(database: ChatDatabase): MessageDao {
        return database.messageDao()
    }

}