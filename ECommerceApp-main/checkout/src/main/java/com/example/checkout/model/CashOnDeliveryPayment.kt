package com.example.checkout.model

class CashOnDeliveryPayment: PaymentMethod {

    override fun processPayment(amount: Double): Boolean {
        // Nothing to process
        return true
    }
}