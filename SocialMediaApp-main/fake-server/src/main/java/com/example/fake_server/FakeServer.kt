package com.example.fake_server

import android.content.Context
import com.example.fake_server.db.FakeServerDatabase
import com.example.fake_server.db.PostDao
import com.example.fake_server.db.PostEntity
import com.example.fake_server.db.UserDao
import com.google.gson.Gson
import okhttp3.Request
import okio.Buffer
import javax.inject.Inject
import kotlin.collections.get

class FakeServer @Inject constructor(
    private val context: Context,
    private val userDao: UserDao,
    private val postDao: PostDao
) {

    suspend fun handleRequest(request: Request): String {
        val endpoint = request.url.encodedPath

        return when (endpoint) {
            "/init" -> populateDatabase()
            "/posts" -> handleGetPosts()
            "/posts/delete" -> handleDeletePost(request)
            "/auth/login" -> handleLogin(request)
            "/auth/logout" -> handleLogout()
            "/publish" -> handlePublishPost(request)
            else -> handleUnknownEndpoint()
        }
    }

    suspend fun populateDatabase(): String {
        if (userDao.getAllUsers().isEmpty()) {
            FakeServerDatabase.prepopulateDatabase(context)
        }
        return Gson().toJson(true)
    }

    suspend fun handleGetPosts(): String {
        val posts = postDao.getAllPosts()
        return Gson().toJson(posts)
    }

    suspend fun handleDeletePost(request: Request): String {
        return try {
            val body = request.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
            } ?: throw IllegalArgumentException("Missing request body")

            val deletePostRequest = Gson().fromJson(body, Map::class.java)

            val postId = deletePostRequest["postId"] as? String
                ?: throw IllegalArgumentException("Missing 'postId'")

            val result = postDao.deletePost(postId) > -1

            Gson().toJson(result)
        } catch (e: Exception) {
            Gson().toJson(mapOf("error" to e.message))
        }
    }

    suspend fun handleLogin(request: Request): String {
        return try {
            val body = request.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
            } ?: throw IllegalArgumentException("Missing request body")

            val loginRequest = Gson().fromJson(body, Map::class.java)

            val username = loginRequest["username"] as? String
                ?: throw IllegalArgumentException("Missing 'username'")
            val passwordHash = loginRequest["passwordHash"] as? String
                ?: throw IllegalArgumentException("Missing 'passwordHash'")

            val user = userDao.getUserByUsername(username)

            if (user != null && user.passwordHash == passwordHash) {
                Gson().toJson(
                    mapOf(
                        "id" to user.id,
                        "username" to user.username,
                        "email" to user.email,
                        "displayName" to user.displayName,
                        "bio" to user.bio,
                        "profileImageUrl" to user.profileImageUrl,
                        "token" to "fake_token"
                    )
                )
            } else {
                Gson().toJson(mapOf("error" to "Invalid credentials"))
            }
        } catch (e: Exception) {
            Gson().toJson(mapOf("error" to e.message))
        }
    }

    fun handleLogout(): String {
        return Gson().toJson(mapOf("message" to "Logout successful"))
    }

    suspend fun handlePublishPost(request: Request): String {
        return try {
            val body = request.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
            } ?: throw IllegalArgumentException("Missing request body")

            val loginRequest = Gson().fromJson(body, Map::class.java)

            val username = loginRequest["username"] as? String
                ?: throw IllegalArgumentException("Missing 'username'")
            val postContent = loginRequest["postContent"] as? String
                ?: throw IllegalArgumentException("Missing 'postContent'")

            val author = userDao.getUserByUsername(username)
                ?: throw IllegalArgumentException("Invalid username")

            val result = postDao.insertPost(
                PostEntity(
                    authorId = author.id,
                    content = postContent,
                    imageUrl = "",
                    timestamp = System.currentTimeMillis()
                )
            ) > 0

            Gson().toJson(mapOf("result" to result))
        } catch (e: Exception) {
            Gson().toJson(mapOf("error" to e.message))
        }
    }

    private fun handleUnknownEndpoint(): String {
        return Gson().toJson(mapOf("error" to "Endpoint not found"))
    }

}