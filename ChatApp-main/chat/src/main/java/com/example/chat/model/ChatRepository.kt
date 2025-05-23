package com.example.chat.model

import com.example.core.model.Contact

interface ChatRepository {

    suspend fun fetchConversations(): List<Conversation>

    suspend fun fetchMessages(contactId: Int): List<Message>

    suspend fun getContact(contactId: Int): Contact

    suspend fun sendMessage(contactId: Int, message: Message): Boolean

    suspend fun deleteMessage(messageId: Int): Boolean

    suspend fun deleteConversation(chatId: Int): Boolean

    suspend fun receiveMessage(): List<Message>
}