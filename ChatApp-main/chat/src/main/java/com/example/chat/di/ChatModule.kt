package com.example.chat.di

import com.example.chat.model.ChatRepository
import com.example.chat.model.ChatRepositoryImpl
import com.example.core.db.ContactDao
import com.example.core.db.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ChatModule {

    @Provides
    fun provideChatRepository(contactDao: ContactDao, messageDao: MessageDao): ChatRepository {
        return ChatRepositoryImpl(contactDao, messageDao)
    }

}