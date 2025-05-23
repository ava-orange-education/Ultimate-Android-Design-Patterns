package com.example.socialmediaapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.authentication.view.LoginScreen
import com.example.authentication.viewmodel.AuthViewModel
import com.example.feed.view.FeedScreen
import com.example.feed.viewmodel.FeedViewModel
import com.example.notifications.view.NotificationScreen
import com.example.notifications.viewmodel.NotificationViewModel
import com.example.post_creation.view.PostCreationScreen
import com.example.post_creation.viewmodel.PostCreationViewModel
import com.example.user_profile.view.ProfileScreen
import com.example.user_profile.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = AppScreen.AppFeedScreen.route,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    feedViewModel: FeedViewModel,
    notificationViewModel: NotificationViewModel,
    postCreationViewModel: PostCreationViewModel
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = AppScreen.AppFeedScreen.route) {
            val uiState = feedViewModel.uiState.collectAsStateWithLifecycle()
            FeedScreen(
                uiState = uiState.value,
                contract = feedViewModel
            )
        }
        composable(route = AppScreen.AppUserProfileScreen.route) {
            val authUiState = authViewModel.uiState.collectAsStateWithLifecycle()
            if (!authUiState.value.isLoggedIn) {
                LoginScreen(contract = authViewModel)
            } else {
                val profileUiState = profileViewModel.uiState.collectAsStateWithLifecycle()
                ProfileScreen(
                    uiState = profileUiState.value,
                    authContract = authViewModel,
                    profileContract = profileViewModel
                )
            }
        }
        composable(route = AppScreen.AppNotificationScreen.route) {
            val uiState = notificationViewModel.uiState.collectAsStateWithLifecycle()
            NotificationScreen(
                uiState = uiState.value,
                contract = notificationViewModel
            )
        }
        composable(route = AppScreen.AppPostCreationScreen.route) {
            val uiState = postCreationViewModel.uiState.collectAsStateWithLifecycle()
            PostCreationScreen(
                uiState = uiState.value,
                contract = postCreationViewModel
            )
        }
    }
}