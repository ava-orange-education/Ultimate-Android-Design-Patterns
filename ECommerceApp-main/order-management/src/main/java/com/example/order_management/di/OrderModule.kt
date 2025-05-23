package com.example.order_management.di

import android.app.Application
import com.example.order_management.db.Order
import com.example.order_management.db.OrderDao
import com.example.order_management.db.OrderDatabase
import com.example.order_management.model.OrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OrderModule {

    @Provides
    @Singleton
    fun provideOrderDatabase(application: Application): OrderDatabase {
        return OrderDatabase.getInstance(application)
    }

    @Provides
    fun provideOrderDao(database: OrderDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderDao: OrderDao): OrderRepository {
        return OrderRepository(orderDao)
    }

}