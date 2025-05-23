package com.example.core.model

data class ApiUser(
    val id: Int,
    val username: String,
    val email: String,
    val displayName: String,
    val bio: String,
    val profileImageUrl: String,
    val token: String
)