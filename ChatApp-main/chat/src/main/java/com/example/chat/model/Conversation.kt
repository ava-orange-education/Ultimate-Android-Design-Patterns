package com.example.chat.model

import com.example.core.model.Contact

data class Conversation(
    val id: Int = 0,
    val contact: Contact = Contact(),
    val lastMessage: Message = Message(timestamp = 0)
//    val messages: List<Message> = emptyList(),
)
