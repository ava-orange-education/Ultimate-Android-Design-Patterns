package com.example.ecommerceapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.authentication.view.AuthScreen
import com.example.cart.view.CartScreen
import com.example.checkout.navigation.includeCheckoutGraph
import com.example.core.viewmodel.CartViewModel
import com.example.order_management.view.OrderScreen
import com.example.order_management.viewmodel.OrderListViewModel
import com.example.product_catalog.navigation.includeProductGraph
import com.example.product_catalog.viewmodel.ProductViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = AppScreen.AppProductListScreen.route,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    orderListViewModel: OrderListViewModel
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        includeProductGraph(
            route = AppScreen.AppProductListScreen.route,
            navController = navController,
            productViewModel = productViewModel,
            cartViewModel = cartViewModel
        )
        composable(route = AppScreen.AppCartScreen.route) {
            CartScreen(
                cartViewModel = cartViewModel,
                navgateToCheckout = {
                    navController.navigate(AppScreen.AppChechoutScreen.route)
                }
            )
        }
        includeCheckoutGraph(
            route = AppScreen.AppChechoutScreen.route,
            navController = navController
        )
        composable(route = AppScreen.AppOrderScreen.route) {
            OrderScreen(
                viewModel = orderListViewModel
            )
        }
        composable(route = AppScreen.AppProfileScreen.route) {
            AuthScreen()
        }
    }
}