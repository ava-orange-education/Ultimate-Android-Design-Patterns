package com.example.order_management.viewmodel

import androidx.lifecycle.ViewModel
import com.example.order_management.db.Order
import com.example.order_management.model.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {

    private val _order = MutableStateFlow<Order?>(null)
    val order: StateFlow<Order?> = _order.asStateFlow()

}