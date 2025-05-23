package com.example.core.model

interface AuthRepository {

    suspend fun getUsername(): String?

    suspend fun loginUser(username: String, password: String): ApiUser

    suspend fun logout(username: String): ApiUser

    suspend fun isUserLoggedIn(): Boolean
}