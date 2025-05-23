package com.example.chat.intent

import com.example.chat.model.Conversation

data class ChatListState(
    val conversations: List<Conversation> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)