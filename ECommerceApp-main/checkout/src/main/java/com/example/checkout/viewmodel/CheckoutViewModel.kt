package com.example.checkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.db.CartItem
import com.example.core.repository.CartRepository
import com.example.order_management.db.Order
import com.example.order_management.db.OrderItem
import com.example.order_management.model.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _fullName = MutableStateFlow<String>("")
    val fullName: StateFlow<String> = _fullName

    private val _phoneNumber = MutableStateFlow<String>("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _streetAddress = MutableStateFlow<String>("")
    val streetAddress: StateFlow<String> = _streetAddress

    private val _postalCodeOrZipCode = MutableStateFlow<String>("")
    val postalCodeOrZipCode: StateFlow<String> = _postalCodeOrZipCode

    private val _city = MutableStateFlow<String>("")
    val city: StateFlow<String> = _city

    private val _stateOrProvinceOrRegion = MutableStateFlow<String>("")
    val stateOrProvinceOrRegion: StateFlow<String> = _stateOrProvinceOrRegion

    private val _country = MutableStateFlow<String>("")
    val country: StateFlow<String> = _country

    private val _paymentMethod = MutableStateFlow<String>("")
    val paymentMethod: StateFlow<String> = _paymentMethod

    private val _cardNumber = MutableStateFlow<String>("")
    val cardNumber: StateFlow<String> = _cardNumber

    private val _expirationDate = MutableStateFlow<String>("")
    val expirationDate: StateFlow<String> = _expirationDate

    private val _cvv = MutableStateFlow<String>("")
    val cvv: StateFlow<String> = _cvv

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _total = MutableStateFlow<Double>(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()

    private val _order = MutableStateFlow(null as Order?)
    val order: StateFlow<Order?> = _order.asStateFlow()

    init {
        getCartItemsList()
    }

    fun updateFullName(fullName: String) {
        _fullName.value = fullName
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun updateStreetAddress(streetAddress: String) {
        _streetAddress.value = streetAddress
    }

    fun updatePostalCodeOrZipCode(postalCodeOrZipCode: String) {
        _postalCodeOrZipCode.value = postalCodeOrZipCode
    }

    fun updateCity(city: String) {
        _city.value = city
    }

    fun updateStateOrProvinceOrRegion(stateOrProvinceOrRegion: String) {
        _stateOrProvinceOrRegion.value = stateOrProvinceOrRegion
    }

    fun updateCountry(country: String) {
        _country.value = country
    }

    fun updatePaymentMethod(paymentMethod: String) {
        _paymentMethod.value = paymentMethod
    }

    fun validateCardNumber(cardNumber: String): Boolean {
        val cardNumberNoSpaces = cardNumber.replace(" ", "")
        return cardNumberNoSpaces.length == 16 &&
                cardNumberNoSpaces.matches(Regex("\\d{16}"))
    }

    fun updateCardNumber(cardNumber: String) {
        val cardNumberNoSpaces = cardNumber.replace(" ", "")
        if (cardNumberNoSpaces.length > 16) {
            return
        }
        val newCardNumber = StringBuilder()
        repeat(cardNumberNoSpaces.length) { index ->
            val char = cardNumberNoSpaces[index]
            newCardNumber.append(char)
            if (index % 4 == 3 && index < cardNumberNoSpaces.length - 1) {
                newCardNumber.append(" ")
            }
        }
        _cardNumber.value = newCardNumber.toString()
    }

    fun validateExpirationDate(expirationDate: String): Boolean {
        return expirationDate.length == 5 &&
                expirationDate.matches(Regex("\\d{2}/\\d{2}"))
    }

    fun updateExpirationDate(expirationDate: String) {
        if (expirationDate.length > 5){
            return
        }
        _expirationDate.value = expirationDate
    }

    fun validateCvv(cvv: String): Boolean {
        return cvv.length == 3 && cvv.matches(Regex("\\d{3}"))
    }

    fun updateCvv(cvv: String) {
        if (cvv.length > 3) {
            return
        }
        _cvv.value = cvv
    }

    fun getCartItemsList() {
        viewModelScope.launch {
            _cartItems.value = cartRepository.fetchCartItems()
            _total.value = cartItems.value.sumOf { it.price }
        }
    }

    fun createOrder() {
        viewModelScope.launch {
            val result = orderRepository.createAndSendOrder(
                fullName = fullName.value,
                phoneNumber = phoneNumber.value,
                streetAddress = streetAddress.value,
                postalCodeOrZipCode = postalCodeOrZipCode.value,
                city = city.value,
                stateOrProvinceOrRegion = stateOrProvinceOrRegion.value,
                country = country.value,
                paymentMethod = paymentMethod.value,
                cardNumber = cardNumber.value,
                expirationDate = expirationDate.value,
                cvv = cvv.value,
                items = cartItems.value.toOrderItems()
            )
            if (result){
                cartRepository.clearCart()
            }
        }
//        if (result) {
//            _order.value = order
//        }
    }

    fun List<CartItem>.toOrderItems(): List<OrderItem> = this.map {
        OrderItem(
            orderId = 0,
            productId = it.productId,
            name = it.name,
            price = it.price,
            description = it.description,
            imageId = it.imageId,
            quantity = it.quantity
        )
    }

}