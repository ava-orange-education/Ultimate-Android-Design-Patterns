package com.example.authentication.viewmodel

interface AuthContract {

    fun login(username: String, password: String)

    fun logout(username: String)

}