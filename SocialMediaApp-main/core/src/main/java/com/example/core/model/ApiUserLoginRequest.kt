package com.example.core.model

data class ApiUserLoginRequest(
    val username: String,
    val passwordHash: String
)
