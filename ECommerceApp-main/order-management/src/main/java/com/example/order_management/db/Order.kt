package com.example.order_management.db

import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val phoneNumber: String,
    val streetAddress: String,
    val postalCodeOrZipCode: String,
    val city: String,
    val stateOrProvinceOrRegion: String,
    val country: String,
    val paymentMethod: String,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val total: Double,
    val createdAt: Long = System.currentTimeMillis()
)