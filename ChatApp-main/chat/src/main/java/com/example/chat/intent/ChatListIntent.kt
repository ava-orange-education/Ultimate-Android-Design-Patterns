package com.example.chat.intent

sealed class ChatListIntent {
    object LoadConversations : ChatListIntent()
    data class DeleteConversation(val contactId: Int) : ChatListIntent()
    object ReceiveMessage : ChatListIntent()
}