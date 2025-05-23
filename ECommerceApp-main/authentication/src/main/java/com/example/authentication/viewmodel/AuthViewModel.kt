package com.example.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.repository.AuthRepository
import com.example.core.ApiUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    private val _loginResult = MutableStateFlow<Result<ApiUser>?>(null)
    val loginResult: StateFlow<Result<ApiUser>?> = _loginResult.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.getAuthToken().collect { token ->
                _authToken.value = token
                Log.d("AuthRepository", "Login successful. Token: ${authToken.value}")
            }
        }
        viewModelScope.launch {
            authRepository.getUsername().collect { username ->
                _username.value = username
                Log.d("AuthRepository", "Login successful. Username: ${_username.value}")
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val user = authRepository.loginUser(username, password)
                _loginResult.value = Result.success(user)
                authRepository.getAuthToken().collect { token ->
                    _authToken.value = token
                }
                authRepository.getUsername().collect { username ->
                    _username.value = username
                }
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            }
        }
    }

    fun logout(username: String){
        viewModelScope.launch {
            authRepository.logout(username)
            _loginResult.value = null
            _authToken.value = null
            _username.value = null
        }
    }
}
