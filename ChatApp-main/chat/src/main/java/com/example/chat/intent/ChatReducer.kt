package com.example.chat.intent

import com.example.chat.model.ChatRepository
import com.example.chat.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * [ChatReducer] is responsible for managing the state of the chat feature.
 * It processes [ChatIntent]s, interacts with the [ChatRepository], and updates the [ChatState].
 *
 * @property repository The [ChatRepository] instance used to interact with the data layer.
 */
class ChatReducer(
    private val repository: ChatRepository
) {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> get() = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Processes a given ChatIntent, performing the corresponding action.
     *
     * This function acts as a dispatcher for different chat-related intents.
     * It determines the specific action to take based on the type of the provided intent.
     *
     * @param intent The ChatIntent to process. It can be one of:
     *               - ChatIntent.LoadMessages: Triggers the loading of messages for a specific contact.
     *               - ChatIntent.SendMessage: Triggers sending a message to a specific contact.
     *               - ChatIntent.DeleteMessage: Triggers the deletion of a specific message from a contact.
     *
     * @see ChatIntent
     * @see loadMessages
     * @see sendMessage
     * @see deleteMessage
     */
    fun process(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.LoadMessages -> loadMessages(intent.contactId)
            is ChatIntent.SendMessage -> sendMessage(intent.contactId, intent.message)
            is ChatIntent.DeleteMessage -> deleteMessage(intent.contactId, intent.messageId)
        }
    }

    private fun loadMessages(contactId: Int) {
        _state.value = _state.value.copy(isLoading = true)
        scope.launch {
            try {
                val contact = repository.getContact(contactId)
                val messages = repository.fetchMessages(contactId)
                _state.value = ChatState(
                    contact = contact,
                    messages = messages,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = ChatState(isLoading = false, error = e.message)
            }
        }
    }

    private fun sendMessage(contactId: Int, message: Message) {
        scope.launch {
            try {
                repository.sendMessage(contactId, message)
                loadMessages(contactId)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    private fun deleteMessage(contactId: Int, messageId: Int) {
        scope.launch {
            try {
                repository.deleteMessage(messageId)
                loadMessages(contactId)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

}