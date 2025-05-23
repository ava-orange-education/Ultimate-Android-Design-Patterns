package com.example.chat.intent

import com.example.chat.model.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatListReducer (
    private val repository: ChatRepository
) {

    private val _state = MutableStateFlow(ChatListState())
    val state: StateFlow<ChatListState> get() = _state.asStateFlow()

    private val _showNotification = MutableStateFlow(false)
    val showNotification: StateFlow<Boolean> get() = _showNotification.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun process(intent: ChatListIntent) {
        when (intent) {
            is ChatListIntent.LoadConversations -> loadConversations()
            is ChatListIntent.DeleteConversation -> deleteConversation(intent.contactId)
            is ChatListIntent.ReceiveMessage -> receiveMessage()
        }
    }

    fun loadConversations() {
        _state.value = _state.value.copy(isLoading = true)
        scope.launch {
            try {
                val conversations = repository.fetchConversations()
                _state.value = ChatListState(conversations = conversations, isLoading = false)
            } catch (e: Exception) {
                _state.value = ChatListState(isLoading = false, error = e.message)
            }
        }
    }

    fun deleteConversation(chatId: Int) {
        scope.launch {
            repository.deleteConversation(chatId)
            loadConversations()
        }
    }

    fun receiveMessage() {
        scope.launch {
            val receivedMessages = repository.receiveMessage()
            _state.value = _state.value.copy(
                conversations = _state.value.conversations.filter {
                    it.contact.id != receivedMessages.first().contactId
                }.toMutableList().apply {
                    add(0, _state.value.conversations.first {
                        it.contact.id == receivedMessages.first().contactId
                    }.copy(lastMessage = receivedMessages.first()))
                }
            )
            _showNotification.value = true
        }
    }

    fun setShownNotificationToFalse() {
        _showNotification.value = false
    }

}