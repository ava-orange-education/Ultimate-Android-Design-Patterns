package com.example.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Comment
import com.example.core.model.UserProfile
import com.example.feed.model.FeedRepository
import com.example.feed.view.FeedUiState
import com.example.user_profile.model.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    val feedRepository: FeedRepository,
    val profileRepository: ProfileRepository
) : FeedContract, ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isReady = feedRepository.initServerDb()
                    )
                }
            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update { currentState ->
                    currentState.copy(
                        loggedUser = profileRepository.getUserProfile(),
                        posts = feedRepository.fetchPosts()
                    )
                }
            }
        }
    }

    fun setState(state: FeedUiState) {
        _uiState.value = state
    }

    override fun fetchPosts() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    loggedUser = profileRepository.getUserProfile(),
                    posts = feedRepository.fetchPosts()
                )
            }
        }
    }

    override fun deletePost(postId: String) {
        viewModelScope.launch {
            feedRepository.deletePost(postId)
            _uiState.update { currentState ->
                currentState.copy(
                    posts = currentState.posts.filter {
                        it.id.toString() != postId
                    }
                )
            }
        }
    }

    override fun likeOrUnlikePost(postId: String) {
        if (isPostLiked(postId)) {
            unlikePost(postId)
        } else {
            likePost(postId)
        }
    }

    fun likePost(postId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                posts = currentState.posts.map {
                    if (it.id.toString() == postId) {
                        it.copy(likes = mutableListOf<UserProfile>().apply {
                            addAll(it.likes)
                            add(uiState.value.loggedUser)
                        })
                    } else it
                }
            )
        }
    }

    fun unlikePost(postId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                posts = currentState.posts.map {
                    if (it.id.toString() == postId) {
                        it.copy(likes = mutableListOf<UserProfile>().apply {
                            addAll(it.likes.filter {
                                it.username != uiState.value.loggedUser.username
                            })
                        })
                    } else it
                }
            )
        }
    }

    override fun addComment(postId: String, comment: Comment) {
        TODO("Not yet implemented")
    }

    override fun isPostLiked(postId: String): Boolean {
        return uiState.value.posts.find {
            it.id.toString() == postId
        }?.likes?.map {
            it.username
        }?.contains(uiState.value.loggedUser.username) == true
    }

    override fun isPostCommented(postId: String): Boolean {
        return uiState.value.posts.find {
            it.id.toString() == postId
        }?.comments?.map {
            it.author.username
        }?.contains(uiState.value.loggedUser.username) == true
    }
}