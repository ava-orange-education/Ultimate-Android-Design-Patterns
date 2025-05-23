package com.example.feed.model

import com.example.core.model.ApiRepository
import com.example.core.model.Post
import com.example.core.model.UserProfile
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FeedRepositoryTest {

    private val mockApiRepository = mockk<ApiRepository>()
    private lateinit var feedRepository: FeedRepositoryImpl

    @Before
    fun setUp() {
        feedRepository = FeedRepositoryImpl(mockApiRepository)
    }

    @Test
    fun `fetchPosts should return list of posts`() = runTest {
        val mockPosts = listOf(
            Post(id = 1, content = "Test Post 1", author = UserProfile(username = "user1")),
            Post(id = 2, content = "Test Post 2", author = UserProfile(username = "user2"))
        )
        coEvery { mockApiRepository.getPosts() } returns mockPosts

        val result = feedRepository.fetchPosts()

        assertEquals(2, result.size)
        assertEquals("Test Post 1", result[0].content)
    }

    @Test
    fun `deletePost should remove post from backend`() = runTest {
        coEvery { mockApiRepository.deletePost("1") } just Runs

        feedRepository.deletePost("1")

        coVerify { mockApiRepository.deletePost("1") }
    }
}
