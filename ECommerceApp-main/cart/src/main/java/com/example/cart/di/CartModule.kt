package com.example.cart.di

import android.app.Application
import com.example.cart.model.CartRepositoryImpl
import com.example.core.db.CartDao
import com.example.core.db.CartDatabase
import com.example.core.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CartModule {

    @Provides
    @Singleton
    fun provideCartDatabase(application: Application): CartDatabase {
        return CartDatabase.getInstance(application)
    }

    @Provides
    fun provideCartDao(database: CartDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

}