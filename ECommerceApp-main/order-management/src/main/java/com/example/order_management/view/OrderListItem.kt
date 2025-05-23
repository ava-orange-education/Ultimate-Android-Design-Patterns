package com.example.order_management.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cart.view.CartListItem
import com.example.core.db.CartItem
import com.example.core.formatTimestampOnlyDate
import com.example.core.formatTimestampOnlyTime
import com.example.core.formatToUSD
import com.example.order_management.db.OrderWithItems

@Composable
fun OrderListItem(
    order: OrderWithItems,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    showClearIcon: Boolean = true
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "${order.order.fullName} on " +
                            "${order.order.createdAt.formatTimestampOnlyDate()} at " +
                            order.order.createdAt.formatTimestampOnlyTime()
                )
                Spacer(modifier = Modifier.weight(1f))
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
            order.orderItems.forEach {
                CartListItem(
                    cartItem = CartItem(
                        productId = it.productId,
                        name = it.name,
                        price = it.price,
                        quantity = it.quantity,
                        imageId = it.imageId,
                        description = it.description
                    ),
                    showClearIcon = false
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(8.dp).align(Alignment.End),
                text = order.order.total.formatToUSD(),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}