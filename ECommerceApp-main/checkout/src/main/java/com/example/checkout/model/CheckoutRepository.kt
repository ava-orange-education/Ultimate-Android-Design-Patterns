package com.example.checkout.model

import com.example.order_management.db.Order
import javax.inject.Inject

class CheckoutRepository @Inject constructor(

) {

    fun saveOrder(order: Order): Boolean {
        return true
    }

    fun saveOrderLocally(order: Order) {

    }
}