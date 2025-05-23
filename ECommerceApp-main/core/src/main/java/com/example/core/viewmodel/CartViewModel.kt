package com.example.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.db.CartItem
import com.example.core.repository.AuthRepository
import com.example.core.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _total = MutableStateFlow<Double>(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()

    private val _isUserLoggedIn = MutableStateFlow<Boolean>(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    var isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        getCartItemsList()
        viewModelScope.launch {
            _isUserLoggedIn.value = authRepository.isUserLoggedIn()
        }
    }

    fun getCartItemsList() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(1000)
            _cartItems.value = cartRepository.fetchCartItems()
            _total.value = cartItems.value.sumOf { it.price }
            _isRefreshing.value = false
        }
    }

    fun addProductToCart(
        productId: String,
        quantity: Int,
        id: Int,
        name: String,
        price: Double,
        description: String,
        imageId: Int
    ) {
        viewModelScope.launch {
            cartRepository.addItemToCart(
                CartItem(
                    productId = productId,
                    quantity = quantity,
                    id = id,
                    name = name,
                    price = price,
                    description = description,
                    imageId = imageId
                )
            )
            _cartItems.value = cartRepository.fetchCartItems()
            _total.value = cartItems.value.sumOf { it.price }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeItemFromCart(cartItem)
            _cartItems.value = cartRepository.fetchCartItems()
            _total.value = cartItems.value.sumOf { it.price }
        }
    }
}