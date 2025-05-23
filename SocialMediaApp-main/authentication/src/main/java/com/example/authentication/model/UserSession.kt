package com.example.authentication.model

data class UserSession(
    val userId: Int,
    val token: String
)