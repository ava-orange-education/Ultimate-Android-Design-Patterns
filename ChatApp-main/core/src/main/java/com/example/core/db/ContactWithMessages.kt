package com.example.core.db

import androidx.room.Embedded
import androidx.room.Relation

data class ContactWithMessages(
    @Embedded val contact: ContactEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "contactId"
    )
    val messages: List<MessageEntity>
)
