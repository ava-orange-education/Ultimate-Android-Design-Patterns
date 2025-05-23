package com.example.order_management.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    suspend fun getAllOrders(): List<OrderWithItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(cartItems: List<OrderItem>)

    @Transaction
    suspend fun insertOrderWithItems(order: Order, cartItems: List<OrderItem>) {
        val orderId = insertOrder(order)
        val updatedCartItems = cartItems.map { it.copy(orderId = orderId.toInt()) }
        insertCartItems(updatedCartItems)
    }

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    suspend fun deleteOrderItemsByOrderId(orderId: Int)

    @Transaction
    suspend fun deleteOrderWithItems(order: Order) {
        deleteOrderItemsByOrderId(order.id)
        deleteOrder(order)
    }
}