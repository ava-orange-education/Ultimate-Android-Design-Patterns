package com.example.order_management.db

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithItems(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val orderItems: List<OrderItem>
)