package com.example.core.model

import com.example.fake_server.db.PostWithAuthor
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun initServerDb(): Boolean {
        return apiService.initServerDb()
    }

    suspend fun getPosts(): List<Post> {
        return apiService.getPosts().map { it.toPost() }
    }

    suspend fun deletePost(postId: String) {
        val request = ApiDeletePostRequest(postId)
        apiService.deletePosts(request)
    }

    fun PostWithAuthor.toPost(): Post {
        return Post(
            id = this.post.id,
            author = UserProfile(
                id = this.author.id,
                username = this.author.username,
                displayName = this.author.displayName,
                profileImageUrl = this.author.profileImageUrl
            ),
            content = this.post.content,
            imageUrl = this.post.imageUrl,
            likes = emptyList(),
            comments = emptyList(),
            timestamp = this.post.timestamp
        )
    }


}