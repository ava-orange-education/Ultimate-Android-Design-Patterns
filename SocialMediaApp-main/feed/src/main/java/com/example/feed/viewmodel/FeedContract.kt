package com.example.feed.viewmodel

import com.example.core.model.Comment

interface FeedContract {

    fun fetchPosts()

    fun deletePost(postId: String)

    fun likeOrUnlikePost(postId: String)

    fun addComment(postId: String, comment: Comment)

    fun isPostLiked(postId: String): Boolean

    fun isPostCommented(postId: String): Boolean

}