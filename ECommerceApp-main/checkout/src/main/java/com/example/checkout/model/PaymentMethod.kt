package com.example.checkout.model

interface PaymentMethod {

    fun processPayment(amount: Double): Boolean

}