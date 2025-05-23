package com.example.core.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


@androidx.room.Database(entities = [CartItem::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}