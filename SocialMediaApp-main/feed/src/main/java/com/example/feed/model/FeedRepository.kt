package com.example.feed.model

import com.example.core.model.Comment
import com.example.core.model.Post

interface FeedRepository {

    suspend fun initServerDb(): Boolean

    suspend fun fetchPosts(): List<Post>

    suspend fun deletePost(postId: String)

    fun likePost(postId: String)

    fun addComment(postId: String, comment: Comment)
}