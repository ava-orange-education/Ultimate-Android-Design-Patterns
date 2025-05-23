package com.example.user_profile.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.example.core.model.UserProfile
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileScreen_displaysUserProfileCorrectly() {
        val testProfile = UserProfile(username = "testUser", displayName = "Test User")

        composeTestRule.setContent {
            ProfileScreen(uiState = ProfileUiState(userProfile = testProfile))
        }

        with(composeTestRule) {
            onNodeWithText("@testUser").assertExists()
            onNodeWithText("Test User").assertExists()
        }
    }

    @Test
    fun profileScreen_updatesDisplayName() {
        val testProfile = UserProfile(username = "testUser", displayName = "Old Name")

        composeTestRule.setContent {
            ProfileScreen(
                uiState = ProfileUiState(userProfile = testProfile)
            )
        }

        with(composeTestRule) {
            onNodeWithTag("Edit").performClick()
            val textField = onNodeWithText("Old Name")
            textField.performTextClearance()
            textField.performTextInput("New Name")
            onNodeWithTag("Apply").performClick()
            onNodeWithText("New Name").assertExists()
        }
    }

}