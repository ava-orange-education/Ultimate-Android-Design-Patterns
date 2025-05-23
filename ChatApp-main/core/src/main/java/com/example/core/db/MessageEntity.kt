package com.example.core.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

const val MSG_SENT = 0
const val MSG_RECEIVED = 1

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = ContactEntity::class,
            parentColumns = ["id"],
            childColumns = ["contactId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contactId: Int,
    val direction: Int, // 0 for sent, 1 for received
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
