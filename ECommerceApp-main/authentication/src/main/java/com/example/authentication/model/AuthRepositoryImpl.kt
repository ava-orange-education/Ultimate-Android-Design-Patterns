package com.example.authentication.model

import com.example.core.ApiService
import com.example.core.ApiUser
import com.example.core.ApiUserLoginRequest
import com.example.core.ApiUserLogoutRequest
import com.example.core.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore
): AuthRepository {

    private val authToken: Flow<String?> = authDataStore.authToken
    private val username: Flow<String?> = authDataStore.username

    override suspend fun getAuthToken(): Flow<String?> = authToken

    override suspend fun getUsername(): Flow<String?> = username

    override suspend fun loginUser(username: String, password: String): ApiUser {
        val request = ApiUserLoginRequest(username = username, password = password)
        val result = apiService.login(request)
        authDataStore.saveAuthToken(result.token)
        authDataStore.saveUsername(result.username)
        return result
    }

    override suspend fun logout(username: String): ApiUser {
        val request = ApiUserLogoutRequest(username = username)
        authDataStore.clearAuthToken()
        return apiService.logout(request)
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return authDataStore.isUserLoggedIn()
    }

}
