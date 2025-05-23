package com.example.authentication.view

data class AuthUiState(
    val username: String = "",
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)
