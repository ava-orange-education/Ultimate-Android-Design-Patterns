package com.example.socialmediaapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.authentication.view.LoggedInProvider
import com.example.socialmediaapp.R
import com.example.socialmediaapp.navigation.AppScreen
import com.example.socialmediaapp.ui.theme.SocialMediaAppTheme

@Composable
fun MyBottomAppBar(
    navController: NavHostController,
    currentDestination: String?,
    isLoggedIn: Boolean
){
    val navigationItems = listOf(
        NavigationItem(
            label = stringResource(R.string.feed),
            route = AppScreen.AppFeedScreen.route,
            selectedIcon = Icons.Default.ArtTrack,
            unselectedIcon = Icons.Default.ArtTrack
        ),
        NavigationItem(
            label = stringResource(R.string.create_post),
            route = AppScreen.AppPostCreationScreen.route,
            selectedIcon = Icons.Default.EditNote,
            unselectedIcon = Icons.Default.EditNote
        ),
        NavigationItem(
            label = stringResource(R.string.notifications),
            route = AppScreen.AppNotificationScreen.route,
            selectedIcon = Icons.Filled.Notifications,
            unselectedIcon = Icons.Outlined.Notifications
        ),
        if (isLoggedIn) {
            NavigationItem(
                label = stringResource(R.string.profile),
                route = AppScreen.AppUserProfileScreen.route,
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        } else {
            NavigationItem(
                label = "Login",
                route = AppScreen.AppUserProfileScreen.route,
                selectedIcon = Icons.AutoMirrored.Default.Login,
                unselectedIcon = Icons.AutoMirrored.Default.Login
            )
        }
    )
    NavigationBar {
        var selectedItem = when (currentDestination) {
            AppScreen.AppFeedScreen.route -> 0
            AppScreen.AppPostCreationScreen.route -> 1
            AppScreen.AppNotificationScreen.route -> 2
            AppScreen.AppUserProfileScreen.route -> 3
            else -> 0
        }
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                },
                icon = {
                    BadgedBox(
                        badge = {}
                    ) {
                        Icon(
                            imageVector = when (selectedItem == index) {
                                true -> item.selectedIcon
                                else -> item.unselectedIcon
                            },
                            contentDescription = item.label
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun MyBottomAppBarPreview(
    @PreviewParameter(LoggedInProvider::class) isLoggedIn: Boolean
){
    SocialMediaAppTheme {
        Surface {
            MyBottomAppBar(
                navController = rememberNavController(),
                currentDestination = AppScreen.AppFeedScreen.route,
                isLoggedIn = isLoggedIn
            )
        }
    }
}