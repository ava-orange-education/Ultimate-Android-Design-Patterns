package com.example.contacts.di

import com.example.contacts.model.ContactRepository
import com.example.contacts.model.ContactRepositoryImpl
import com.example.core.db.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ContactModule {

    @Provides
    fun provideContactRepository(contactDao: ContactDao): ContactRepository {
        return ContactRepositoryImpl(contactDao)
    }

}