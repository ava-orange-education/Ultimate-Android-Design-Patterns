package com.example.user_profile.model

import com.example.authentication.model.AuthDataStore
import com.example.authentication.model.AuthDataStore.Companion.KEY_BIO
import com.example.authentication.model.AuthDataStore.Companion.KEY_DISPLAY_NAME
import com.example.authentication.model.AuthDataStore.Companion.KEY_USERNAME
import com.example.authentication.model.AuthDataStore.Companion.KEY_EMAIL
import com.example.authentication.model.AuthDataStore.Companion.KEY_PROFILE_IMAGE_URL
import com.example.core.model.UserProfile
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val authDataStore: AuthDataStore
): ProfileRepository {

    override suspend fun getUserProfile(): UserProfile {
        return UserProfile(
            username = authDataStore.getValue(KEY_USERNAME).firstOrNull() ?: "",
            displayName = authDataStore.getValue(KEY_DISPLAY_NAME).firstOrNull() ?: "",
            email = authDataStore.getValue(KEY_EMAIL).firstOrNull() ?: "",
            bio = authDataStore.getValue(KEY_BIO).firstOrNull() ?: "",
            profileImageUrl = authDataStore.getValue(KEY_PROFILE_IMAGE_URL).firstOrNull() ?: ""
        )
    }
}