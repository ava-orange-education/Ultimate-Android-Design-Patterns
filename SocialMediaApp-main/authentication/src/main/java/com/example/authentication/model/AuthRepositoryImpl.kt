package com.example.authentication.model

import com.example.authentication.model.AuthDataStore.Companion.KEY_BIO
import com.example.authentication.model.AuthDataStore.Companion.KEY_DISPLAY_NAME
import com.example.authentication.model.AuthDataStore.Companion.KEY_EMAIL
import com.example.authentication.model.AuthDataStore.Companion.KEY_PROFILE_IMAGE_URL
import com.example.authentication.model.AuthDataStore.Companion.KEY_TOKEN
import com.example.authentication.model.AuthDataStore.Companion.KEY_USERNAME
import com.example.core.model.ApiUserLoginRequest
import com.example.core.model.ApiUserLogoutRequest
import com.example.core.hash
import com.example.core.model.ApiService
import com.example.core.model.ApiUser
import com.example.core.model.AuthRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore
): AuthRepository {

    override suspend fun getUsername(): String? =
        authDataStore.getValue(KEY_USERNAME).firstOrNull()

    override suspend fun loginUser(username: String, password: String): ApiUser {
        val request = ApiUserLoginRequest(
            username = username,
            passwordHash = password.hash()
        )
        val result = apiService.login(request)
        authDataStore.apply {
            setValue(KEY_TOKEN, result.token)
            setValue(KEY_USERNAME, result.username)
            setValue(KEY_EMAIL, result.email)
            setValue(KEY_DISPLAY_NAME, result.displayName)
            setValue(KEY_BIO, result.bio)
            setValue(KEY_PROFILE_IMAGE_URL, result.profileImageUrl)
        }
        return result
    }

    override suspend fun logout(username: String): ApiUser {
        val request = ApiUserLogoutRequest(username = username)
        authDataStore.clearValue(KEY_TOKEN)
        return apiService.logout(request)
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return authDataStore.isUserLoggedIn()
    }

}
