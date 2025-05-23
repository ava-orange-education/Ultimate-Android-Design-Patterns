package com.example.socialmediaapp.navigation

sealed class AppScreen(val route: String) {
    data object AppFeedScreen : AppScreen("app_feed_screen")
    data object AppUserProfileScreen : AppScreen("app_user_profile_screen")
    data object AppNotificationScreen : AppScreen("notification_screen")
    data object AppPostCreationScreen : AppScreen("post_creation_screen")
}