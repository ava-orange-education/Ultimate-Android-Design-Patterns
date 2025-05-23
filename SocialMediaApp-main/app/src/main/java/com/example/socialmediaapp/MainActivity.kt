package com.example.socialmediaapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.authentication.viewmodel.AuthViewModel
import com.example.feed.viewmodel.FeedViewModel
import com.example.notifications.viewmodel.NotificationViewModel
import com.example.post_creation.viewmodel.PostCreationViewModel
import com.example.socialmediaapp.navigation.AppNavHost
import com.example.socialmediaapp.ui.MyBottomAppBar
import com.example.socialmediaapp.ui.MyTopAppBar
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.example.user_profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialMediaAppTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()

                val authViewModel: AuthViewModel = hiltViewModel()
                val profileViewModel: ProfileViewModel = hiltViewModel()
                val feedViewModel: FeedViewModel = hiltViewModel()
                val notificationViewModel: NotificationViewModel = hiltViewModel()
                val postCreationViewModel: PostCreationViewModel = hiltViewModel()

                val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyTopAppBar(
                            currentDestination = currentBackStackEntry?.destination?.route
                        )
                    },
                    bottomBar = {
                        MyBottomAppBar(
                            navController = navController,
                            currentDestination = currentBackStackEntry?.destination?.route,
                            isLoggedIn = authUiState.isLoggedIn
                        )
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost(
                            navController = navController,
                            authViewModel = authViewModel,
                            profileViewModel = profileViewModel,
                            feedViewModel = feedViewModel,
                            notificationViewModel = notificationViewModel,
                            postCreationViewModel = postCreationViewModel
                        )
                    }
                }
            }
        }
    }
}