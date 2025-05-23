package com.example.product_catalog.model

data class Product (
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageId: Int
)