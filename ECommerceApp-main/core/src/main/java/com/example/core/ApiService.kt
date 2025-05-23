package com.example.core

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {

    @GET("/categories")
    suspend fun getCategories(): List<ApiCategory>

    @GET("/products")
    suspend fun getProducts(): List<ApiProduct>

//    @POST("/auth/register")
//    suspend fun register(@Body request: ApiUserRegistrationRequest): ApiUser

    @POST("/auth/login")
    suspend fun login(@Body request: ApiUserLoginRequest): ApiUser

    @POST("/auth/logout")
    suspend fun logout(@Body request: ApiUserLogoutRequest): ApiUser

}