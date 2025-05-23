package com.example.checkout.di

import com.example.checkout.model.CheckoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CheckoutModule {

    @Provides
    @Singleton
    fun provideCheckoutRepository(): CheckoutRepository {
        return CheckoutRepository()
    }

}