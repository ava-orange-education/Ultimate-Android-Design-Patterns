package com.example.checkout.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class OrderStatus {
    PROCESSING,
    CONFIRMED
}

@Composable
fun OrderConfirmationScreen() {
    val orderStatus = remember {
        mutableStateOf(OrderStatus.PROCESSING)
    }

    LaunchedEffect(Unit) {
        // Simulate order confirmation process
        Thread.sleep(3000)
        orderStatus.value = OrderStatus.CONFIRMED
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (orderStatus.value == OrderStatus.PROCESSING) {
            CircularProgressIndicator(
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Processing Order...",
                style = MaterialTheme.typography.headlineSmall
            )
        } else {
            Image(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            Text(
                text = "Order Confirmed",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview
@Composable
fun OrderConfirmationScreenPreview() {
    Surface {
        OrderConfirmationScreen()
    }
}