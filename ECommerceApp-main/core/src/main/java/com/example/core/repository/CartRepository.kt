package com.example.core.repository

import com.example.core.db.CartItem

interface CartRepository {

    suspend fun fetchCartItems(): List<CartItem>

    suspend fun addItemToCart(cartItem: CartItem)

    suspend fun removeItemFromCart(cartItem: CartItem)

    suspend fun clearCart()
}