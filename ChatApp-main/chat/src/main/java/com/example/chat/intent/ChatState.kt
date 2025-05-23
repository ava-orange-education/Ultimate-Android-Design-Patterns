package com.example.chat.intent

import com.example.core.model.Contact
import com.example.chat.model.Message

data class ChatState(
    val contact: Contact = Contact(),
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)