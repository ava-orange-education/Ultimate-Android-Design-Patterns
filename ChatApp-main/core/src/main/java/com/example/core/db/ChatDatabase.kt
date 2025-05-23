package com.example.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.core.R

@Database(
    entities = [
        ContactEntity::class,
        MessageEntity::class
    ], version = 1
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "database.db"
                )
                    .addCallback(databaseCallback())
                    .build().also { INSTANCE = it }
            }
        }

        private fun databaseCallback(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }
        }

        suspend fun prepopulateDatabase(context: Context) {
            getInstance(context).apply {
                contactDao().insertAll(prepopulateContacts(context))
                messageDao().insertAll(prepopulateMessages())
            }
        }

        private fun prepopulateContacts(context: Context): List<ContactEntity> {
            return listOf(
                ContactEntity(
                    name = "John Doe",
                    phoneNumber = "+11234567890",
                    profilePicture = "android.resource://${context.packageName}/${R.drawable.john_doe}"
                ),
                ContactEntity(
                    name = "Jane Doe",
                    phoneNumber = "+10987654321",
                    profilePicture = "android.resource://${context.packageName}/${R.drawable.jane_doe}"
                ),
                ContactEntity(
                    name = "Bob Smith",
                    phoneNumber = "+11112223333",
                    profilePicture = ""
                ),
                ContactEntity(
                    name = "Alice Johnson",
                    phoneNumber = "+14445556666",
                    profilePicture = ""
                ),
                ContactEntity(
                    name = "Tom Wilson",
                    phoneNumber = "+17778889999",
                    profilePicture = ""
                ),
                ContactEntity(
                    name = "Sara Lee",
                    phoneNumber = "+12223334444",
                    profilePicture = ""
                ),
                ContactEntity(
                    name = "Mike Brown",
                    phoneNumber = "+15556667777",
                    profilePicture = ""
                ),
                ContactEntity(
                    name = "Emily Davis",
                    phoneNumber = "+18889990000",
                    profilePicture = ""
                ),
                ContactEntity(
                    name = "David Lee",
                    phoneNumber = "+13334445555",
                    profilePicture = ""
                ),
                ContactEntity(
                    name = "Lisa Chen",
                    phoneNumber = "+16667778888",
                    profilePicture = ""
                )
            )
        }

        private fun prepopulateMessages(): List<MessageEntity> {
            return listOf(
                MessageEntity(
                    contactId = 1,
                    direction = MSG_SENT,
                    text = "Hello, how are you?",
                    timestamp = System.currentTimeMillis()
                ),
                MessageEntity(
                    contactId = 1,
                    direction = MSG_RECEIVED,
                    text = "I'm good, thanks! How about you?",
                    timestamp = System.currentTimeMillis()
                ),
                MessageEntity(
                    contactId = 2,
                    direction = MSG_RECEIVED,
                    text = "Hi, what's up?",
                    timestamp = System.currentTimeMillis()
                ),
                MessageEntity(
                    contactId = 2,
                    direction = MSG_SENT,
                    text = "Not much, just hanging out.",
                    timestamp = System.currentTimeMillis()
                ),
                MessageEntity(
                    contactId = 3,
                    direction = MSG_SENT,
                    text = "What's the plan for the weekend?",
                    timestamp = System.currentTimeMillis(),
                )
            )
        }
    }
}