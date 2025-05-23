package com.example.user_profile.viewmodel

interface ProfileContract {

    fun fetchUserInfo()

    fun updateDisplayName(newName: String)

    fun updateBio(newBio: String)

}