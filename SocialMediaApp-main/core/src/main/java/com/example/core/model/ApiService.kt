package com.example.core.model

import com.example.fake_server.db.PostWithAuthor
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/init")
    suspend fun initServerDb(): Boolean

    @GET("/posts")
    suspend fun getPosts(): List<PostWithAuthor>

    @POST("/posts/delete")
    suspend fun deletePosts(@Body request: ApiDeletePostRequest): Boolean

    @POST("/auth/login")
    suspend fun login(@Body request: ApiUserLoginRequest): ApiUser

    @POST("/auth/logout")
    suspend fun logout(@Body request: ApiUserLogoutRequest): ApiUser

    @POST("/publish")
    suspend fun publishPost(@Body request: ApiPublishPostRequest): ApiPublishPostResponse

}