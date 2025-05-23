package com.example.post_creation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.AuthRepository
import com.example.core.model.Post
import com.example.feed.model.FeedRepository
import com.example.post_creation.model.PostCreationRepository
import com.example.post_creation.view.PostCreationUiState
import com.example.post_creation.view.PostStatus
import com.example.user_profile.model.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    private val postCreationRepository: PostCreationRepository,
    private val profileRepository: ProfileRepository,
    private val feedRepository: FeedRepository,
    private val authRepository: AuthRepository
) : PostCreationContract, ViewModel() {

    private val _uiState = MutableStateFlow<PostCreationUiState>(PostCreationUiState())
    val uiState: StateFlow<PostCreationUiState> = _uiState.asStateFlow()

    override fun fetchUserInfo() {
        viewModelScope.launch {
            if (!authRepository.isUserLoggedIn()) {
                _uiState.value = _uiState.value.copy(
                    status = PostStatus.MISSING_AUTH
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    status = PostStatus.WRITING,
                    post = _uiState.value.post.copy(author = profileRepository.getUserProfile())
                )
            }
        }
    }

    override fun onPostContentChanged(content: String) {
        _uiState.value = _uiState.value.copy(
            post = _uiState.value.post.copy(content = content)
        )
    }

    override fun publishPost(post: Post) {
        viewModelScope.launch {
            val result = postCreationRepository.createPost(post)
            if (result){
                _uiState.value = _uiState.value.copy(
                    post = _uiState.value.post.copy(content = ""),
                    status = PostStatus.PUBLISHED
                )
                feedRepository.fetchPosts()
            } else {
                _uiState.value = _uiState.value.copy(
                    status = PostStatus.ERROR
                )
            }
        }
    }

    override fun returnToWriting(){
        _uiState.value = _uiState.value.copy(
            status = PostStatus.WRITING
        )
    }

}