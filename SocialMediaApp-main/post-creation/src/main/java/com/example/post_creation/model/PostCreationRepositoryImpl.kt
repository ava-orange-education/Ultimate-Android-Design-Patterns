package com.example.post_creation.model

import com.example.core.model.ApiPublishPostRequest
import com.example.core.model.ApiService
import com.example.core.model.Post

class PostCreationRepositoryImpl(
    private val apiService: ApiService
) : PostCreationRepository {

    override suspend fun createPost(post: Post): Boolean {
        val apiPublishPostRequest = ApiPublishPostRequest(
            username = post.author.username,
            postContent = post.content
        )
        val response = apiService.publishPost(apiPublishPostRequest)
        return response.result
    }
}