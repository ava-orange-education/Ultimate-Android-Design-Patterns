package com.example.ecommerceapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.checkout.navigation.CheckoutScreen
import com.example.ecommerceapp.navigation.AppScreen
import com.example.product_catalog.navigation.ProductScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentDestination: String?
) {
    val title = currentDestination?.let {
        when {
            it == ProductScreen.ProductListScreen.route -> "Products"
            it.startsWith(ProductScreen.ProductDetailsScreen.route) -> "Product Details"
            it == AppScreen.AppCartScreen.route -> "Cart"
            it == AppScreen.AppChechoutScreen.route -> "Checkout"
            it == CheckoutScreen.CheckoutAddInfoScreen.route -> "Add Info"
            it == CheckoutScreen.CheckoutSummaryScreen.route -> "Checkout Summary"
            it == AppScreen.AppOrderScreen.route -> "Orders"
            it == AppScreen.AppProfileScreen.route -> "Profile"
            else -> ""
        }
    } ?: ""
    TopAppBar(
        title = {
            Text(
                text = title
            )
        }
    )
}