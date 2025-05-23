package com.example.order_management.model

import com.example.core.db.CartItem
import com.example.order_management.db.Order
import com.example.order_management.db.OrderDao
import com.example.order_management.db.OrderItem
import com.example.order_management.db.OrderWithItems
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao
) {

    suspend fun createAndSendOrder(
        fullName: String,
        phoneNumber: String,
        streetAddress: String,
        postalCodeOrZipCode: String,
        city: String,
        stateOrProvinceOrRegion: String,
        country: String,
        paymentMethod: String,
        cardNumber: String,
        expirationDate: String,
        cvv: String,
        items: List<OrderItem>
    ): Boolean {
        orderDao.insertOrderWithItems(
            Order(
                fullName = fullName,
                phoneNumber = phoneNumber,
                streetAddress = streetAddress,
                postalCodeOrZipCode = postalCodeOrZipCode,
                city = city,
                stateOrProvinceOrRegion = stateOrProvinceOrRegion,
                country = country,
                paymentMethod = paymentMethod,
                cardNumber = cardNumber,
                expirationDate = expirationDate,
                cvv = cvv,
                total = items.sumOf { it.price * it.quantity }
            ),
            items
        )
        return true
    }

    suspend fun getOrders(): List<OrderWithItems> {
        return orderDao.getAllOrders()
    }

    suspend fun deleteOrder(order: Order) {
        orderDao.deleteOrderWithItems(order)
    }
}