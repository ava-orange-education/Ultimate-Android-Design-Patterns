package com.example.fake_server

import com.example.fake_server.db.CategoryDao
import com.example.fake_server.db.ProductDao
import com.example.fake_server.db.UserDao
import com.example.fake_server.db.UserEntity
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Buffer
import javax.inject.Inject

class FakeServer @Inject constructor(
    private val userDao: UserDao,
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao,
) {

    fun handleRequest(request: Request): String {
        val endpoint = request.url.encodedPath

        return when (endpoint) {
            "/categories" -> handleGetCategories()
            "/products" -> handleGetProducts()
            "/auth/login" -> handleLogin(request)
            "/auth/logout" -> handleLogout()
//            "/products/details" -> handleGetProductDetails(request)
            else -> handleUnknownEndpoint()
        }
    }

    fun handleGetCategories(): String {
        val categories = runBlocking {
            categoryDao.getAllCategories()
        }
        return Gson().toJson(categories)
    }

    fun handleGetProducts(): String {
        val products = runBlocking {
            productDao.getAllProducts()
        }
        return Gson().toJson(products)
    }

    private fun handleUnknownEndpoint(): String {
        return Gson().toJson(mapOf("error" to "Endpoint not found"))
    }

    fun saveOrder(): Boolean {
        // Simulate saving order to the server
        return true
    }

    suspend fun registerUser(user: UserEntity): Boolean {
        if (userDao.isEmailRegistered(user.email) > 0) {
            return false
        }
        userDao.insertUser(user)
        return true
    }

    fun handleLogin(request: Request): String {
        return try {
            val body = request.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
            } ?: throw IllegalArgumentException("Missing request body")

            val loginRequest = Gson().fromJson(body, Map::class.java)

            val username = loginRequest["username"] as? String
                ?: throw IllegalArgumentException("Missing 'username'")
            val password = loginRequest["password"] as? String
                ?: throw IllegalArgumentException("Missing 'password'")

            val user = UserEntity(
                username = username, password = password, email = "fake-email"
            )
//            val user = runBlocking { loginUser(username, password) }

             if (user != null) {
                Gson().toJson(
                    mapOf(
                        "id" to user.id,
                        "username" to user.username,
                        "email" to user.email,
                        "token" to "fake-token"
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

    suspend fun loginUser(email: String, password: String): UserEntity? {
        return userDao.getUserByEmailAndPassword(email, password)
    }


}