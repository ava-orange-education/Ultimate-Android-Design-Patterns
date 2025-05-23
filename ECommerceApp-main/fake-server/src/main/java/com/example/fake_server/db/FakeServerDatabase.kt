package com.example.fake_server.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fake_server.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        CategoryEntity::class,
        ProductEntity::class
    ], version = 1
)
abstract class FakeServerDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: FakeServerDatabase? = null

        fun getInstance(context: Context): FakeServerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FakeServerDatabase::class.java,
                    "fake_server_database.db"
                )
                    .addCallback(databaseCallback(context))
                    .build().also { INSTANCE = it }
            }
        }

        private fun databaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        getInstance(context).productDao().insertAll(prepopulateProducts())
                        getInstance(context).categoryDao().insertAll(prepopulateCategories())
                    }
                }
            }
        }

        private fun prepopulateCategories(): List<CategoryEntity> {
            return listOf(
                CategoryEntity(id = 1, name = "Electronics"),
                CategoryEntity(id = 2, name = "Clothing"),
                CategoryEntity(id = 3, name = "Home & Kitchen"),
                CategoryEntity(id = 4, name = "Beauty & Personal Care"),
                CategoryEntity(id = 5, name = "Books & Literature"),
                CategoryEntity(id = 6, name = "Toys & Games"),
                CategoryEntity(id = 7, name = "Sports & Outdoors"),
                CategoryEntity(id = 8, name = "Grocery & Food"),
                CategoryEntity(id = 9, name = "Health & Wellness"),
                CategoryEntity(id = 10, name = "Jewelry & Watches")

            )
        }

        private fun prepopulateProducts(): List<ProductEntity> {
            return listOf(
                ProductEntity(
                    categoryId = 1,
                    name = "5G Smartphone",
                    price = 899.99,
                    description = "128GB storage, triple-lens camera, 6.5-inch AMOLED display.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "Noise-Canceling Wireless Headphones",
                    price = 199.99,
                    description = "Over-ear design, 30-hour battery life, fast charging.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "14-Inch Lightweight Laptop",
                    price = 749.99,
                    description = "Intel Core i5, 8GB RAM, 256GB SSD, Full HD screen.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "Compact Mirrorless Camera",
                    price = 1299.99,
                    description = "24MP resolution, 4K video recording, interchangeable lens.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "Portable Power Bank",
                    price = 49.99,
                    description = "20,000mAh capacity, dual USB outputs, LED indicator.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "Streaming Media Player",
                    price = 59.99,
                    description = "4K Ultra HD, HDR support, voice-enabled remote.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "Fitness Smartwatch",
                    price = 149.99,
                    description = "GPS tracking, heart rate monitor, waterproof design.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "55-Inch Smart TV",
                    price = 899.99,
                    description = "4K resolution, built-in apps, voice control compatibility.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "Portable Bluetooth Speaker",
                    price = 89.99,
                    description = "10-hour battery life, water-resistant, 360-degree sound.",
                    imageId = R.drawable.ecommerce_package
                ),
                ProductEntity(
                    categoryId = 1,
                    name = "Tablet with Stylus Support",
                    price = 399.99,
                    description = "10.5-inch display, 64GB storage, detachable keyboard compatibility.",
                    imageId = R.drawable.ecommerce_package
                )

            )
        }
    }
}
