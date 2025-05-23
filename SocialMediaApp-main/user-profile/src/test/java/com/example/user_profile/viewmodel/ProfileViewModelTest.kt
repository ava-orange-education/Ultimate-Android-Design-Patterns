package com.example.user_profile.viewmodel

import com.example.core.model.UserProfile
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
class ProfileViewModelTest {

    private val mockProfileRepository = mockk<ProfileRepository>(relaxed = true)
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher()) // Override the Main dispatcher
        viewModel = ProfileViewModel(mockProfileRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher after test
    }

    @Test
    fun `fetchUserInfo should update state with user profile`() = runTest {
        val mockProfile = UserProfile(username = "testUser", displayName = "Test User")
        coEvery { mockProfileRepository.getUserProfile() } returns mockProfile

        viewModel.fetchUserInfo()
        advanceUntilIdle() // Wait for coroutine to complete

        val state = viewModel.uiState.value
        assertEquals("testUser", state.userProfile.username)
        assertEquals("Test User", state.userProfile.displayName)
    }

}
