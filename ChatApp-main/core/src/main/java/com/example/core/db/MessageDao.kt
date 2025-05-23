package com.example.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("""
        SELECT * FROM messages m
        INNER JOIN contacts c ON m.contactId = c.id
        WHERE m.id IN (
            SELECT MAX(id) from messages GROUP BY contactId
        )
        ORDER BY m.timestamp DESC
    """)
    suspend fun getLatestMessages(): List<ContactWithMessages>

    @Query("SELECT * FROM messages WHERE contactId = :contactId")
    suspend fun getMessagesForContact(contactId: Int): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<MessageEntity>)

    @Query("DELETE FROM messages WHERE id = :messageId")
    suspend fun delete(messageId: Int)

    @Query("DELETE FROM messages WHERE contactId = :contactId")
    suspend fun deleteAllByContactId(contactId: Int)

}