package com.example.checkout.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.checkout.view.CheckoutAddInfoScreen
import com.example.checkout.view.CheckoutSummaryScreen
import com.example.checkout.view.OrderConfirmationScreen
import com.example.checkout.viewmodel.CheckoutViewModel

fun NavGraphBuilder.includeCheckoutGraph(navController: NavHostController, route: String) {
    navigation(
        route = route,
        startDestination = CheckoutScreen.CheckoutAddInfoScreen.route
    ) {
        composable(route = CheckoutScreen.CheckoutAddInfoScreen.route) { navBackStackEntry ->
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry(route)
            }
            val viewModel: CheckoutViewModel = hiltViewModel(parentEntry)
            CheckoutAddInfoScreen(
                navgateNext = {
                    navController.navigate(CheckoutScreen.CheckoutSummaryScreen.route)
                },
                viewModel = viewModel
            )
        }
        composable(route = CheckoutScreen.CheckoutSummaryScreen.route) { navBackStackEntry ->
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry(route)
            }
            val viewModel: CheckoutViewModel = hiltViewModel(parentEntry)
            CheckoutSummaryScreen(
                viewModel = viewModel,
                navgateNext = {
                    navController.navigate(CheckoutScreen.OrderConfirmationScreen.route)
                },
            )
        }
        composable(route = CheckoutScreen.OrderConfirmationScreen.route) { navBackStackEntry ->
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry(route)
            }
            val viewModel: CheckoutViewModel = hiltViewModel(parentEntry)
            OrderConfirmationScreen(
//                viewModel = viewModel
            )
        }
    }
}