package com.example.order_management.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.order_management.db.Order
import com.example.order_management.db.OrderWithItems
import com.example.order_management.model.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {

    private val _orders = MutableStateFlow<List<OrderWithItems>>(emptyList())
    val orders: StateFlow<List<OrderWithItems>> = _orders.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    var isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        getOrderList()
    }

    fun getOrderList() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(1000)
            _orders.value = orderRepository.getOrders()
            _isRefreshing.value = false
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.deleteOrder(order)
            _orders.value = orderRepository.getOrders()
        }
    }

}