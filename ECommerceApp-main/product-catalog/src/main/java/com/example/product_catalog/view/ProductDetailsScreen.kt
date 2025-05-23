package com.example.product_catalog.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.viewmodel.CartViewModel
import com.example.product_catalog.viewmodel.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val viewModel: ProductDetailsViewModel = hiltViewModel()
    val itemId = navBackStackEntry?.arguments?.getString("itemId")
    if (itemId != null){
        viewModel.getProductDetails(itemId)
    }
    val product = viewModel.product.collectAsStateWithLifecycle()

    product.value?.let {
        Column {
            Image(
                painter = painterResource(id = it.imageId),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = it.name,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = it.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.padding(8.dp).align(Alignment.End),
                text = it.price.toString(),
                style = MaterialTheme.typography.headlineMedium
            )
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    cartViewModel.addProductToCart(
                        productId = it.id.toString(),
                        quantity = 1,
                        id = 0,
                        name = it.name,
                        price = it.price,
                        description = it.description,
                        imageId = it.imageId)
                }
            ) {
                Text(text = "Add to cart")
            }
        }
    }
}