package com.example.feed.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.core.model.Comment
import com.example.core.model.Post
import com.example.core.model.UserProfile
import com.example.feed.viewmodel.FeedContract
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class FeedScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun feedScreen_displaysPostsCorrectly() {
        val testPosts = listOf(
            Post(id = 1, content = "Test Post 1", author = UserProfile(username = "user1")),
            Post(id = 2, content = "Test Post 2", author = UserProfile(username = "user2"))
        )

        composeTestRule.setContent {
            FeedScreen(uiState = FeedUiState(posts = testPosts))
        }

        composeTestRule.onNodeWithText("Test Post 1").assertExists()
        composeTestRule.onNodeWithText("Test Post 2").assertExists()
    }

    @Test
    fun feedScreen_likeButtonUpdatesState() {
        val testPost = Post(id = 1, content = "Test Post", author = UserProfile(username = "user1"))
        val testUiState = FeedUiState(posts = listOf(testPost))
        var liked = false

        composeTestRule.setContent {
            FeedScreen(uiState = testUiState, contract = object : FeedContract {
                override fun fetchPosts() { }

                override fun deletePost(postId: String) { }

                override fun likeOrUnlikePost(postId: String) {
                    liked = !liked
                }

                override fun addComment(postId: String, comment: Comment) { }

                override fun isPostLiked(postId: String): Boolean = liked

                override fun isPostCommented(postId: String): Boolean = false
            })
        }

        composeTestRule.onNodeWithText("Test Post").assertExists()
        composeTestRule.onNodeWithContentDescription("Like Button").performClick()
        assertTrue(liked)
    }

}