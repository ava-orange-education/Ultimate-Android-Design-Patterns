package com.example.user_profile.model

import com.example.core.model.UserProfile

interface ProfileRepository {

    suspend fun getUserProfile(): UserProfile

}