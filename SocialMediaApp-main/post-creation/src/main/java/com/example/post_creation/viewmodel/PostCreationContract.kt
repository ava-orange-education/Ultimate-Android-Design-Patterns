package com.example.post_creation.viewmodel

import com.example.core.model.Post

interface PostCreationContract {

    fun fetchUserInfo()

    fun onPostContentChanged(content: String)

    fun publishPost(post: Post)

    fun returnToWriting()

}