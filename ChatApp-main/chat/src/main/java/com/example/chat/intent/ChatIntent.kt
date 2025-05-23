package com.example.chat.intent

import com.example.chat.model.Message

sealed class ChatIntent {
    data class LoadMessages(val contactId: Int): ChatIntent()
    data class SendMessage(val contactId: Int, val message: Message): ChatIntent()
    data class DeleteMessage(val contactId: Int, val messageId: Int): ChatIntent()
}