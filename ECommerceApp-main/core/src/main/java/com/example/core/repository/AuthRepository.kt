package com.example.core.repository

import com.example.core.ApiUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getAuthToken(): Flow<String?>

    suspend fun getUsername(): Flow<String?>

    suspend fun loginUser(username: String, password: String): ApiUser

    suspend fun logout(username: String): ApiUser

    suspend fun isUserLoggedIn(): Boolean
}