package com.example.ecommerceapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.viewmodel.CartViewModel
import com.example.ecommerceapp.navigation.AppNavHost
import com.example.ecommerceapp.ui.MyBottomAppBar
import com.example.ecommerceapp.ui.MyTopAppBar
import com.example.ecommerceapp.ui.theme.ECommerceAppTheme
import com.example.order_management.viewmodel.OrderListViewModel
import com.example.product_catalog.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceAppTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()

                val productViewModel: ProductViewModel = hiltViewModel()
                val cartViewModel: CartViewModel = hiltViewModel()
                val orderListViewModel: OrderListViewModel = hiltViewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyTopAppBar(
                            currentDestination = currentBackStackEntry?.destination?.route
                        )
                    },
                    bottomBar = {
                        MyBottomAppBar(
                            navController = navController,
                            currentDestination = currentBackStackEntry?.destination?.route,
                            cartViewModel = cartViewModel
                        )
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost(
                            navController = navController,
                            productViewModel = productViewModel,
                            cartViewModel = cartViewModel,
                            orderListViewModel = orderListViewModel
                        )
                    }
                }
            }
        }
    }
}