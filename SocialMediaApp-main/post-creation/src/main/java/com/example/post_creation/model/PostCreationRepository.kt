package com.example.post_creation.model

import com.example.core.model.Post

interface PostCreationRepository {

    suspend fun createPost(post: Post): Boolean

}