package com.example.chat.model

import com.example.core.db.ContactDao
import com.example.core.db.ContactEntity
import com.example.core.db.ContactWithMessages
import com.example.core.db.MSG_RECEIVED
import com.example.core.db.MSG_SENT
import com.example.core.db.MessageDao
import com.example.core.db.MessageEntity
import com.example.core.model.Contact

class ChatRepositoryImpl(
    private val contactDao: ContactDao,
    private val messageDao: MessageDao,
) : ChatRepository {

    override suspend fun fetchConversations(): List<Conversation> {
        return messageDao.getLatestMessages()
            .map { it.toConversation() }
    }

    override suspend fun fetchMessages(contactId: Int): List<Message> {
        return messageDao.getMessagesForContact(contactId)
            .map { it.toMessage() }
    }

    override suspend fun getContact(contactId: Int): Contact {
        return contactDao.getById(contactId).toContact()
    }

    override suspend fun sendMessage(
        contactId: Int,
        message: Message
    ): Boolean {
        val messageEntity = MessageEntity(
            contactId = contactId,
            direction = MSG_SENT,
            text = message.text
        )
        messageDao.insert(messageEntity)
        return true
    }

    override suspend fun deleteMessage(messageId: Int): Boolean {
        messageDao.delete(messageId)
        return true
    }

    override suspend fun deleteConversation(contactId: Int): Boolean {
        messageDao.deleteAllByContactId(contactId)
        return true
    }

    override suspend fun receiveMessage(): List<Message> {
        val randomContact = contactDao.getAll().map {
            it.id
        }.random()
        val randomMessage = listOf(
            "Hello! How are you?",
            "Hey, what's up?",
            "Hi there! How's your day going so far?",
            "Greetings! What are you up to right now?",
            "Hey, have you seen any good movies lately?",
            "Hi, what's something interesting that happened to you today?",
            "Hello! I'd love to hear about your latest adventures.",
            "Hey, how's everything with you?",
            "Hi there, got any fun plans for the weekend?"
        ).random()
        val messageEntity = MessageEntity(
            contactId = randomContact,
            direction = MSG_RECEIVED,
            text = randomMessage
        )
        messageDao.insert(messageEntity)
        return listOf(messageEntity.toMessage())
    }

    fun ContactEntity.toContact(): Contact {
        return Contact(
            id = id,
            name = name,
            phoneNumber = phoneNumber,
            profilePicture = profilePicture
        )
    }

    fun MessageEntity.toMessage(): Message {
        return Message(
            id = id,
            contactId = contactId,
            direction = if (this.direction == MSG_SENT)
                MessageDirection.SENT
            else
                MessageDirection.RECEIVED,
            text = text,
            timestamp = timestamp
        )
    }

    fun ContactWithMessages.toConversation(): Conversation {
        val lastMessage = messages.last()
        return Conversation(
            contact = Contact(
                id = contact.id,
                name = contact.name,
                profilePicture = contact.profilePicture
            ),
            lastMessage = Message(
                id = lastMessage.id,
                contactId = lastMessage.contactId,
                direction = if (lastMessage.direction == MSG_SENT)
                    MessageDirection.SENT
                else
                    MessageDirection.RECEIVED,
                text = lastMessage.text,
                timestamp = lastMessage.timestamp
            ),
        )
    }
}