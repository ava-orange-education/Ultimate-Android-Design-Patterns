package com.example.product_catalog.navigation


sealed class ProductScreen(val route: String) {
    data object ProductListScreen : ProductScreen("product_list_screen")
    data object ProductDetailsScreen : ProductScreen("product_details_screen")
}