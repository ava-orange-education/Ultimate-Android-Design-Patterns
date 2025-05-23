package com.example.checkout.navigation

sealed class CheckoutScreen(val route: String) {
    data object CheckoutAddInfoScreen : CheckoutScreen("checkout_add_info_screen")
    data object CheckoutSummaryScreen : CheckoutScreen("checkout_summary_screen")
    data object OrderConfirmationScreen : CheckoutScreen("order_confirmation_screen")
}