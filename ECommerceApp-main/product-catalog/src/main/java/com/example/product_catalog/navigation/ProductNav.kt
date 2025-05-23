package com.example.product_catalog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.core.viewmodel.CartViewModel
import com.example.product_catalog.view.ProductDetailsScreen
import com.example.product_catalog.view.ProductListScreen
import com.example.product_catalog.viewmodel.ProductViewModel

fun NavGraphBuilder.includeProductGraph(
    navController: NavHostController,
    route: String,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    navigation(
        route = route,
        startDestination = ProductScreen.ProductListScreen.route,
    ) {
        composable(route = ProductScreen.ProductListScreen.route) {
            ProductListScreen(
                viewModel = productViewModel,
                navigateToProductDetails = { productId ->
                    navController.navigate(
                        ProductScreen.ProductDetailsScreen.route +
                            "?itemId=${productId}"
                    )
                }
            )
        }
        composable(
            route = ProductScreen.ProductDetailsScreen.route + "?itemId={itemId}",
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            ProductDetailsScreen(navController, cartViewModel = cartViewModel)
        }
    }
}