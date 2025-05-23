package com.example.feed.viewmodel

import com.example.core.model.Post
import com.example.core.model.UserProfile
import com.example.feed.model.FeedRepository
import com.example.feed.view.FeedUiState
import com.example.user_profile.model.ProfileRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

    private val mockFeedRepository = mockk<FeedRepository>(relaxed = true)
    private val mockProfileRepository = mockk<ProfileRepository>(relaxed = true)
    private lateinit var viewModel: FeedViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher()) // Override the Main dispatcher
        viewModel = FeedViewModel(mockFeedRepository, mockProfileRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher after test
    }

    @Test
    fun `fetchPosts should update state with post list`() = runTest {
        val mockPosts = listOf(
            Post(id = 1, content = "Test Post 1", author = UserProfile(username = "user1"))
        )
        coEvery { mockFeedRepository.fetchPosts() } returns mockPosts

        viewModel.fetchPosts()
        advanceUntilIdle() // Wait for coroutine to complete

        val state = viewModel.uiState.value
        assertEquals(1, state.posts.size)
        assertEquals("Test Post 1", state.posts[0].content)
    }

    @Test
    fun `likeOrUnlikePost should toggle like status`() = runTest {
        val mockPost = Post(id = 1, content = "Test Post", likes = mutableListOf())
        val mockUser = UserProfile(username = "testUser")

        val initialState = FeedUiState(posts = listOf(mockPost), loggedUser = mockUser)
        viewModel.setState(initialState)

        viewModel.likeOrUnlikePost("1")

        val updatedPost = viewModel.uiState.value.posts.first()
        assertEquals(1, updatedPost.likes.size)
        assertEquals("testUser", updatedPost.likes.first().username)

        viewModel.likeOrUnlikePost("1")

        val revertedPost = viewModel.uiState.value.posts.first()
        assertEquals(0, revertedPost.likes.size)
    }
}
