package com.example.order_management.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.view.EmptyScreen
import com.example.order_management.viewmodel.OrderListViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderListViewModel? = null,
) {
    val orders = viewModel?.orders?.collectAsStateWithLifecycle()
    var isRefreshing = viewModel?.isRefreshing?.collectAsStateWithLifecycle()
    val state = rememberPullRefreshState(isRefreshing?.value == true, {
        viewModel?.getOrderList()
    })

    if (orders?.value?.isEmpty() == true) {
        EmptyScreen(text = "No orders yet")
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
                orders?.value?.let {
                    items(orders.value.size) { index ->
                        val order = orders.value[index]
                        OrderListItem(
                            order = order,
                            onClick = { viewModel.deleteOrder(order.order)}
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