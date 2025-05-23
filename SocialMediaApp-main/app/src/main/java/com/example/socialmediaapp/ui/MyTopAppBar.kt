package com.example.socialmediaapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialmediaapp.R
import com.example.socialmediaapp.navigation.AppScreen
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentDestination: String?
) {
    val title = currentDestination?.let {
        when (it) {
            AppScreen.AppFeedScreen.route -> stringResource(R.string.feed)
            AppScreen.AppNotificationScreen.route -> stringResource(R.string.notifications)
            AppScreen.AppUserProfileScreen.route -> stringResource(R.string.profile)
            AppScreen.AppPostCreationScreen.route -> stringResource(R.string.create_post)
            else -> ""
        }
    } ?: ""
    TopAppBar(
        title = {
            Text(
                text = title
            )
        }
    )
}

@Preview
@Composable
fun MyTopAppBarPreview(){
    SocialMediaAppTheme {
        Surface {
            MyTopAppBar(
                currentDestination = AppScreen.AppFeedScreen.route
            )
        }
    }
}