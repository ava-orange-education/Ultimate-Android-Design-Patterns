package com.example.fake_server.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fake_server.R
import com.example.fake_server.hash

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class
    ], version = 1
)
abstract class FakeServerDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: FakeServerDatabase? = null

        fun getInstance(context: Context): FakeServerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FakeServerDatabase::class.java,
                    "fake_server_database.db"
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
                userDao().insertAll(prepopulateUsers(context))
                postDao().insertAll(prepopulatePosts())
            }
        }

        private fun prepopulateUsers(context: Context): List<UserEntity> {
            return listOf(
                UserEntity(
                    id = 1,
                    username = "john_doe",
                    email = "johndoe@example.com",
                    passwordHash = "john_doe".hash(),
                    displayName = "John Doe",
                    bio = "Software engineer with a passion for open-source. Always learning new things.",
                    profileImageUrl = "android.resource://${context.packageName}/${R.drawable.john_doe}"
                ),
                UserEntity(
                    id = 2,
                    username = "jane_doe",
                    email = "janedoe@example.com",
                    passwordHash = "jane_doe".hash(),
                    displayName = "Jane Doe",
                    bio = "Travel enthusiast and aspiring writer. Love discovering new cultures.",
                    profileImageUrl = "android.resource://${context.packageName}/${R.drawable.jane_doe}"
                ),
                UserEntity(
                    id = 3,
                    username = "mike_smith",
                    email = "mikesmith@example.com",
                    passwordHash = "mike_smith".hash(),
                    displayName = "Mike Smith",
                    bio = "Tech geek and coffee lover. Building innovative solutions one line at a time.",
                    profileImageUrl = ""
                ),
                UserEntity(
                    id = 4,
                    username = "emma_johnson",
                    email = "emmajohnson@example.com",
                    passwordHash = "emma_johnson".hash(),
                    displayName = "Emma Johnson",
                    bio = "Lover of books and art. Exploring creativity through words and colors.",
                    profileImageUrl = ""
                ),
                UserEntity(
                    id = 5,
                    username = "chris_brown",
                    email = "chrisbrown@example.com",
                    passwordHash = "chris_brown".hash(),
                    displayName = "Chris Brown",
                    bio = "Music enthusiast and part-time guitarist. Always vibing to good tunes.",
                    profileImageUrl = ""
                ),
                UserEntity(
                    id = 6,
                    username = "sarah_wilson",
                    email = "sarahwilson@example.com",
                    passwordHash = "sarah_wilson".hash(),
                    displayName = "Sarah Wilson",
                    bio = "Fitness and wellness advocate. Believer in a balanced and healthy life.",
                    profileImageUrl = ""
                ),
                UserEntity(
                    id = 7,
                    username = "david_miller",
                    email = "davidmiller@example.com",
                    passwordHash = "david_miller".hash(),
                    displayName = "David Miller",
                    bio = "History buff and documentary lover. Understanding the past to shape the future.",
                    profileImageUrl = ""
                ),
                UserEntity(
                    id = 8,
                    username = "laura_martinez",
                    email = "lauramartinez@example.com",
                    passwordHash = "laura_martinez".hash(),
                    displayName = "Laura Martinez",
                    bio = "Food blogger and home cook. Experimenting with flavors from around the world.",
                    profileImageUrl = ""
                ),
                UserEntity(
                    id = 9,
                    username = "james_anderson",
                    email = "jamesanderson@example.com",
                    passwordHash = "james_anderson".hash(),
                    displayName = "James Anderson",
                    bio = "Outdoor adventurer and photographer. Capturing moments one shot at a time.",
                    profileImageUrl = ""
                ),
                UserEntity(
                    id = 10,
                    username = "olivia_taylor",
                    email = "oliviataylor@example.com",
                    passwordHash = "olivia_taylor".hash(),
                    displayName = "Olivia Taylor",
                    bio = "Science nerd and space enthusiast. Always looking at the stars.",
                    profileImageUrl = ""
                )
            )
        }

        private fun prepopulatePosts(): List<PostEntity> {
             return listOf(
                 PostEntity(
                     id = 1,
                     authorId = 1,
                     content = "Just started working on a new open-source project! Excited to see where this goes. 🚀",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis()
                 ),
                 PostEntity(
                     id = 2,
                     authorId = 1,
                     content = "Learning Kotlin has been a game changer. The more I code, the more I love it!",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 3600000
                 ),
                 PostEntity(
                     id = 3,
                     authorId = 1,
                     content = "Coding is like solving a puzzle. Every challenge is an opportunity to grow. 💡",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 7200000
                 ),
                 PostEntity(
                     id = 4,
                     authorId = 1,
                     content = "Spent the evening debugging... turns out it was just a missing semicolon. Classic. 😅",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 10800000
                 ),
                 PostEntity(
                     id = 5,
                     authorId = 1,
                     content = "Dark mode or light mode? The eternal debate continues. 🌗",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 14400000
                 ),
                 PostEntity(
                     id = 6,
                     authorId = 1,
                     content = "Nothing beats the feeling of seeing your code run flawlessly after hours of work!",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 18000000
                 ),
                 PostEntity(
                     id = 7,
                     authorId = 1,
                     content = "Started a new book on software architecture. So much to learn and improve!",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 21600000
                 ),
                 PostEntity(
                     id = 8,
                     authorId = 1,
                     content = "Coffee and coding: the ultimate productivity duo. ☕💻",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 25200000
                 ),
                 PostEntity(
                     id = 9,
                     authorId = 1,
                     content = "If you could only use one programming language for the rest of your life, what would it be?",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 28800000
                 ),
                 PostEntity(
                     id = 10,
                     authorId = 1,
                     content = "Just pushed some updates to my latest project. Looking forward to feedback!",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 32400000
                 ),
                 PostEntity(
                     id = 11,
                     authorId = 2,
                     content = "Just booked my next trip! Can’t wait to explore new places and meet amazing people. ✈️🌍",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis()
                 ),
                 PostEntity(
                     id = 12,
                     authorId = 2,
                     content = "Writing is my escape. There’s something magical about putting thoughts into words.",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 3600000
                 ),
                 PostEntity(
                     id = 13,
                     authorId = 2,
                     content = "Woke up early to watch the sunrise today. A reminder that simple moments bring the most joy. ☀️",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 7200000
                 ),
                 PostEntity(
                     id = 14,
                     authorId = 2,
                     content = "Packing light for a trip is an art. Still trying to master it. 🎒",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 10800000
                 ),
                 PostEntity(
                     id = 15,
                     authorId = 2,
                     content = "Just finished an amazing book! Any recommendations for my next read? 📚",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 14400000
                 ),
                 PostEntity(
                     id = 16,
                     authorId = 2,
                     content = "Nothing beats a cup of coffee and a good journal to start the day. ☕✍️",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 18000000
                 ),
                 PostEntity(
                     id = 17,
                     authorId = 2,
                     content = "Met some wonderful locals on my last trip. Traveling isn’t just about places, it’s about people. ❤️",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 21600000
                 ),
                 PostEntity(
                     id = 18,
                     authorId = 2,
                     content = "Trying to learn a few words in every language I encounter. Makes traveling even more fun! 🌏",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 25200000
                 ),
                 PostEntity(
                     id = 19,
                     authorId = 2,
                     content = "Some days, all you need is a long walk by the ocean to clear your mind. 🌊",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 28800000
                 ),
                 PostEntity(
                     id = 20,
                     authorId = 2,
                     content = "Finally writing the first chapter of my novel. No more procrastination!",
                     imageUrl = "",
                     timestamp = System.currentTimeMillis() - 32400000
                 )
             )

        }
    }
}