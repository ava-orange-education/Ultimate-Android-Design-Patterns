package com.example.cart.model

import com.example.core.db.CartDao
import com.example.core.db.CartItem
import com.example.core.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
): CartRepository {

    override suspend fun fetchCartItems(): List<CartItem> {
        return cartDao.getAllCartItems()
    }

    override suspend fun addItemToCart(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    override suspend fun removeItemFromCart(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

}