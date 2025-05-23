package com.example.ecommerceapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.checkout.navigation.CheckoutScreen
import com.example.core.viewmodel.CartViewModel
import com.example.ecommerceapp.navigation.AppScreen
import com.example.ecommerceapp.ui.theme.ECommerceAppTheme
import com.example.product_catalog.navigation.ProductScreen

@Composable
fun MyBottomAppBar(navController: NavHostController, currentDestination: String?, cartViewModel: CartViewModel? = null){
    val cartItems = cartViewModel?.cartItems?.collectAsStateWithLifecycle()
    val navigationItems = listOf(
        NavigationItem(
            label = "Products",
            route = AppScreen.AppProductListScreen.route,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List
        ),
        NavigationItem(
            label = "Cart",
            route = AppScreen.AppCartScreen.route,
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart
        ),
        NavigationItem(
            label = "Orders",
            route = AppScreen.AppOrderScreen.route,
            selectedIcon = Icons.Filled.ShoppingBag,
            unselectedIcon = Icons.Outlined.ShoppingBag
        ),
        NavigationItem(
            label = "Profile",
            route = AppScreen.AppProfileScreen.route,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )
    NavigationBar {
        var selectedItem = when (currentDestination) {
            AppScreen.AppProductListScreen.route -> 0
            ProductScreen.ProductListScreen.route -> 0
            ProductScreen.ProductDetailsScreen.route -> 0
            AppScreen.AppCartScreen.route -> 1
            AppScreen.AppChechoutScreen.route -> 1
            CheckoutScreen.CheckoutAddInfoScreen.route -> 1
            CheckoutScreen.CheckoutSummaryScreen.route -> 1
            AppScreen.AppOrderScreen.route -> 2
            AppScreen.AppProfileScreen.route -> 3
            else -> 0
        }
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.label == "Cart" && cartItems?.value?.isNotEmpty() == true) {
                                Badge {
                                    Text("${cartItems.value.size}")
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = when (selectedItem == index) {
                                true -> item.selectedIcon
                                else -> item.unselectedIcon
                            },
                            contentDescription = item.label
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun MyBottomAppBarPreview(){
    ECommerceAppTheme {
        Surface {
            MyBottomAppBar(
                navController = rememberNavController(),
                currentDestination = AppScreen.AppProductListScreen.route
            )
        }
    }
}