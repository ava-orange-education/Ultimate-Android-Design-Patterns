@get:Rule
val composeTestRule = createComposeRule()

@Test
fun testTextUpdatesInCompose() {
    // Set the composable to test
    composeTestRule.setContent {
        ObserverComposable()
    }

    // Verifies the initial text
    composeTestRule.onNodeWithText("Hello, Observer!").assertExists()

    // Simulates the update of the text
    composeTestRule.onNodeWithText("Update Text").performClick()

    // Verifies the new text after the update
    composeTestRule.onNodeWithText("Hello again, Observer!").assertExists()
}
