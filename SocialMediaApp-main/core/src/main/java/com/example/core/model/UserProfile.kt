package com.example.core.model

data class UserProfile(
    val id: Int = 0,
    val username: String = "",
    val email: String = "",
    var displayName: String = "",
    val bio: String = "",
    val profileImageUrl: String = "",
)
