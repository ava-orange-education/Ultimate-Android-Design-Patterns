package com.example.chatapp.navigation

sealed class AppScreen(val route: String) {
    data object ChatListScreen : AppScreen("chat_list_screen")
    data object ChatScreen : AppScreen("chat_screen")
    data object ContactListScreen : AppScreen("contact_list_screen")
}