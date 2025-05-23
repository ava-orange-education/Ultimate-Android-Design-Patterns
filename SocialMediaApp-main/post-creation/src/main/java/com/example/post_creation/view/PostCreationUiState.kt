package com.example.post_creation.view

import com.example.core.model.Post

enum class PostStatus {
    MISSING_AUTH,
    WRITING,
    PUBLISHED,
    ERROR
}

data class PostCreationUiState (
    val post: Post = Post(),
    val status: PostStatus = PostStatus.WRITING
)