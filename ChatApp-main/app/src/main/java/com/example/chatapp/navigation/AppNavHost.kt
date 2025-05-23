package com.example.chatapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.chat.intent.ChatIntent
import com.example.chat.intent.ChatListIntent
import com.example.chat.intent.ChatListReducer
import com.example.chat.intent.ChatListState
import com.example.chat.intent.ChatReducer
import com.example.chat.intent.ChatState
import com.example.chat.view.ChatListScreen
import com.example.chat.view.ChatScreen
import com.example.contacts.intent.ContactListIntent
import com.example.contacts.intent.ContactListReducer
import com.example.contacts.intent.ContactListState
import com.example.contacts.view.ContactListScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = AppScreen.ChatListScreen.route,
    chatListReducer: ChatListReducer,
    chatListState: ChatListState,
    chatReducer: ChatReducer,
    chatState: ChatState,
    contactListReducer: ContactListReducer,
    contactListState: ContactListState,
    onSimulateMessageReceiving: (title: String, message: String) -> Unit
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        val navArgumentContactId = "contactId"
        composable(route = AppScreen.ChatListScreen.route) {
            LaunchedEffect(Unit) {
                chatListReducer.process(ChatListIntent.LoadConversations)
            }

            val showNotification by chatListReducer.showNotification.collectAsState()
            if (showNotification) {
                onSimulateMessageReceiving(
                    chatListState.conversations[0].contact.name,
                    chatListState.conversations[0].lastMessage.text
                )
                chatListReducer.setShownNotificationToFalse()
            }

            ChatListScreen(
                state = chatListState,
                onChatSelected = { contactId ->
                    navController.navigate(AppScreen.ChatScreen.route +
                            "?$navArgumentContactId=$contactId")
                },
                onConversationDelete = { contactId ->
                    chatListReducer.process(ChatListIntent.DeleteConversation(contactId))
                },
                onSimulateMessageReceiving = { title, message ->
                    chatListReducer.process(ChatListIntent.ReceiveMessage)
                }
            )
        }
        composable(
            route = AppScreen.ChatScreen.route + "?$navArgumentContactId={$navArgumentContactId}",
            arguments = listOf(
                navArgument(navArgumentContactId) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val chatId = navBackStackEntry?.arguments?.getInt(navArgumentContactId)
            if (chatId == null) return@composable

            LaunchedEffect(Unit) {
                chatReducer.process(ChatIntent.LoadMessages(chatId))
            }

            ChatScreen(
                state = chatState,
                onMessageSent = { message ->
                    chatReducer.process(ChatIntent.SendMessage(chatId, message))
                },
                onMessageDelete = { messageId ->
                    chatReducer.process(ChatIntent.DeleteMessage(chatId, messageId))
                }
            )
        }
        composable(route = AppScreen.ContactListScreen.route) {
            LaunchedEffect(Unit) {
                contactListReducer.process(ContactListIntent.LoadContacts)
            }

            ContactListScreen(
                state = contactListState,
                onContactSelected = { contactId ->
                    navController.navigate(AppScreen.ChatScreen.route +
                            "?$navArgumentContactId=$contactId")
                }
            )
        }
    }
}