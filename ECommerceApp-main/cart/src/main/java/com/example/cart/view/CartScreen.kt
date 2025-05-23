package com.example.cart.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.db.CartItem
import com.example.core.formatToUSD
import com.example.core.view.EmptyScreen
import com.example.core.viewmodel.CartViewModel

@Composable
fun CartListItem(
    cartItem: CartItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    showClearIcon: Boolean = true
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = cartItem.imageId),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(text = cartItem.name, modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = cartItem.price.formatToUSD(), modifier = Modifier.padding(start = 8.dp))
            if (showClearIcon) {
                IconButton(onClick = onClick) {
                    Image(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel? = null,
    navgateToCheckout: () -> Unit = {}
) {
    val cartItems = cartViewModel?.cartItems?.collectAsStateWithLifecycle()
    val total = cartViewModel?.total?.collectAsStateWithLifecycle()
    val isUserLoggedIn = cartViewModel?.isUserLoggedIn?.collectAsStateWithLifecycle()

    var isRefreshing = cartViewModel?.isRefreshing?.collectAsStateWithLifecycle()
    val state = rememberPullRefreshState(isRefreshing?.value == true, {
        cartViewModel?.getCartItemsList()
    })

    if (cartItems?.value?.isEmpty() == true) {
        EmptyScreen(text = "Empty cart")
        return
    }

    Box (
        modifier = Modifier.pullRefresh(state)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                cartItems?.value?.let {
                    items(cartItems.value.size) { index ->
                        val cartItem = cartItems.value[index]
                        CartListItem(
                            cartItem = cartItem,
                            onClick = {
                                cartViewModel.removeFromCart(cartItem)
                            }
                        )
                    }
                }
            }
            Column(
                modifier = modifier
                    .wrapContentHeight().fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp).align(Alignment.End),
                    text = "Total: " + total?.value?.formatToUSD(),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = isUserLoggedIn?.value == true,
                    onClick = navgateToCheckout
                ) {
                    Text(text = "Proceed to checkout")
                }
                isUserLoggedIn?.value?.let {
                    if (!(it)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "You need to be logged in to proceed to checkout."
                        )
                    }
                }
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshing?.value == true,
            state = state
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CartScreen()
}