package com.example.core.model

data class Comment(
    val id: Int = 0,
    val author: UserProfile = UserProfile(),
    val content: String = ""
)