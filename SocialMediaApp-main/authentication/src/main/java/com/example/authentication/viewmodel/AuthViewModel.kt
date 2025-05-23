package com.example.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.view.AuthUiState
import com.example.core.model.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : AuthContract, ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val isLoggedIn = authRepository.isUserLoggedIn()
                if (isLoggedIn) {
                    currentState.copy(
                        username = authRepository.getUsername() ?: "",
                        isLoggedIn = authRepository.isUserLoggedIn())
                } else currentState
            }
        }
    }

    override fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val user = authRepository.loginUser(username, password)
                _uiState.update {
                    currentState -> currentState.copy(
                        username = user.username,
                        isLoggedIn = true
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    currentState -> currentState.copy(
                        errorMessage = "Login failed!"
                    )
                }
            }
        }
    }

    override fun logout(username: String){
        viewModelScope.launch {
            authRepository.logout(username)
            _uiState.update {
                currentState -> currentState.copy(
                    isLoggedIn = false
                )
            }
        }
    }
}
