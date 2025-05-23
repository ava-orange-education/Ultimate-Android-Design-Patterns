package com.example.user_profile.model

import com.example.authentication.model.AuthDataStore
import com.example.authentication.model.AuthDataStore.Companion.KEY_BIO
import com.example.authentication.model.AuthDataStore.Companion.KEY_DISPLAY_NAME
import com.example.authentication.model.AuthDataStore.Companion.KEY_EMAIL
import com.example.authentication.model.AuthDataStore.Companion.KEY_PROFILE_IMAGE_URL
import com.example.authentication.model.AuthDataStore.Companion.KEY_USERNAME
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProfileRepositoryTest {

    private val mockAuthDataStore = mockk<AuthDataStore>()
    private lateinit var profileRepository: ProfileRepositoryImpl

    @Before
    fun setUp() {
        profileRepository = ProfileRepositoryImpl(mockAuthDataStore)
    }

    @Test
    fun `getUserProfile should return correct user profile`() = runTest {
        val keys = listOf(KEY_USERNAME, KEY_DISPLAY_NAME, KEY_BIO, KEY_PROFILE_IMAGE_URL, KEY_EMAIL)
        val values = listOf("testUser", "Test User", "Test Bio", "testUrl", "testEmail")

        keys.forEachIndexed { index, key ->
            every { mockAuthDataStore.getValue(key) } returns flowOf(values[index])
        }

        val result = profileRepository.getUserProfile()

        val resultKeys = listOf(result.username, result.displayName, result.bio, result.profileImageUrl, result.email)

        resultKeys.forEachIndexed { index, key ->
            assertEquals(values[index], key)
        }
    }

}
