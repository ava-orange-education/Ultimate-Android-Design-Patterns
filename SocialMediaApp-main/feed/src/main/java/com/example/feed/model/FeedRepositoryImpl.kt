package com.example.feed.model

import com.example.core.model.ApiRepository
import com.example.core.model.Comment
import com.example.core.model.Post
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepository
) : FeedRepository {

    override suspend fun initServerDb(): Boolean {
        return apiRepository.initServerDb()
    }

    override suspend fun fetchPosts(): List<Post> {
        return apiRepository.getPosts()
    }

    override suspend fun deletePost(postId: String) {
        apiRepository.deletePost(postId)
    }

    override fun likePost(postId: String) {
        TODO("Not yet implemented")
    }

    override fun addComment(postId: String, comment: Comment) {
        TODO("Not yet implemented")
    }
}