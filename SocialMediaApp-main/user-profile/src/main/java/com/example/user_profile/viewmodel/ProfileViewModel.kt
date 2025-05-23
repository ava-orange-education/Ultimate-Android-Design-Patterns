package com.example.user_profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user_profile.model.ProfileRepository
import com.example.user_profile.view.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val profileRepository: ProfileRepository
) : ProfileContract, ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        fetchUserInfo()
    }

    override fun fetchUserInfo() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(userProfile = profileRepository.getUserProfile())
            }
        }
    }

    override fun updateDisplayName(newDisplayName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userProfile = currentState.userProfile.copy(displayName = newDisplayName)
            )
        }
    }

    override fun updateBio(newBio: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userProfile = currentState.userProfile.copy(bio = newBio)
            )
        }
    }

}