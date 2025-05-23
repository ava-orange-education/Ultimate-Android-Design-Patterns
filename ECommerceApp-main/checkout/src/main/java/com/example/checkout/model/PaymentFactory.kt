package com.example.checkout.model

enum class PaymentMethodEnum(val value: String) {
    CREDIT_CARD("Credit/Debit Card"),
    CASH_ON_DELIVERY("Cash on Delivery")
}

fun PaymentMethodEnum.fromValue(value: String): PaymentMethodEnum? = PaymentMethodEnum.entries.find { it.value == value }

class PaymentFactory {

    fun createPaymentMethod(
        paymentMethod: PaymentMethodEnum,
        cardNumber: String? = null,
        expirationDate: String? = null,
        cvv: String? = null
    ): PaymentMethod {
        return when (paymentMethod) {
            PaymentMethodEnum.CREDIT_CARD -> if (cardNumber != null && expirationDate != null && cvv != null) {
                CreditCardPayment(cardNumber, expirationDate, cvv)
            } else {
                throw IllegalArgumentException("Missing credit card details")
            }
            PaymentMethodEnum.CASH_ON_DELIVERY -> CashOnDeliveryPayment()
            else -> throw IllegalArgumentException("Invalid payment method: $paymentMethod")
        }
    }
}
