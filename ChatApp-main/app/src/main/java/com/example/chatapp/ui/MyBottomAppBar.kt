package com.example.chatapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.outlined.Contacts
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.navigation.AppScreen
import com.example.chatapp.ui.theme.ChatAppTheme

@Composable
fun MyBottomAppBar(
    navController: NavHostController,
    currentDestination: String?
){
    if (currentDestination?.startsWith(AppScreen.ChatScreen.route) == true){
        return
    }
    val navigationItems = listOf(
        NavigationItem(
            label = stringResource(R.string.chats),
            route = AppScreen.ChatListScreen.route,
            selectedIcon = Icons.AutoMirrored.Default.Chat,
            unselectedIcon = Icons.AutoMirrored.Outlined.Chat
        ),
        NavigationItem(
            label = stringResource(R.string.contacts),
            route = AppScreen.ContactListScreen.route,
            selectedIcon = Icons.Default.Contacts,
            unselectedIcon = Icons.Outlined.Contacts
        )
    )
    NavigationBar {
        var selectedItem = when (currentDestination) {
            AppScreen.ChatListScreen.route -> 0
            AppScreen.ChatScreen.route -> 0
            AppScreen.ContactListScreen.route -> 1
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
fun MyBottomAppBarPreview(){
    ChatAppTheme {
        Surface {
            MyBottomAppBar(
                navController = rememberNavController(),
                currentDestination = AppScreen.ChatListScreen.route
            )
        }
    }
}