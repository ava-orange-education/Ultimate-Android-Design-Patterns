package com.example.core.model

data class Post(
    val id: Int = 0,
    val author: UserProfile = UserProfile(),
    val content: String = "",
    val imageUrl: String = "",
    val likes: List<UserProfile> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val timestamp: Long = 0L
)
