package com.example.ecommerceapp.navigation

sealed class AppScreen(val route: String) {
    data object AppProductListScreen : AppScreen("app_product_list_screen")
    data object AppCartScreen : AppScreen("app_cart_app_screen")
    data object AppChechoutScreen : AppScreen("app_checkout_screen")
    data object AppOrderScreen : AppScreen("app_order_app_screen")
    data object AppProfileScreen : AppScreen("app_profile_app_screen")
}