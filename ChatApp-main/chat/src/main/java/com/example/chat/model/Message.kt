package com.example.chat.model

sealed class MessageDirection {
    object SENT: MessageDirection()
    object RECEIVED: MessageDirection()
}

data class Message(
    val id: Int = 0,
    val contactId: Int = 0,
    val direction: MessageDirection = MessageDirection.SENT,
    val text: String = "",
    val timestamp: Long,
    val isRead: Boolean = false
)
