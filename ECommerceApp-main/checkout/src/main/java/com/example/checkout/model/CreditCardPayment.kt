package com.example.checkout.model

class CreditCardPayment(
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String
): PaymentMethod {

    override fun processPayment(amount: Double): Boolean {
        // Logic to process credit card payment
        // Example:
        // 1. Validate card details (cardNumber, expirationDate, cvv)
        // 2. Check if card has sufficient funds
        // 3. Authorize transaction with payment gateway
        // 4. Deduct the amount from the card
        // 5. Return true if successful, false otherwise
        return true
    }
}