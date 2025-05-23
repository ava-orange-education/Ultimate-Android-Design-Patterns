package com.example.feed.view

import com.example.core.model.Post
import com.example.core.model.UserProfile

data class FeedUiState(
    val isReady: Boolean = false,
    val posts: List<Post> = emptyList(),
    val loggedUser: UserProfile = UserProfile()
)
