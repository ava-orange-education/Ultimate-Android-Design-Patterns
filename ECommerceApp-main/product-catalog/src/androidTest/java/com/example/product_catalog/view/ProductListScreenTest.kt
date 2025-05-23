package com.example.product_catalog.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.product_catalog.model.Product
import org.junit.Rule
import org.junit.Test

class ProductListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun productListScreen_displaysProductsCorrectly() {
        val mockProducts = listOf(
            Product(1, "Product 1", "Description 1", 10.0, 0),
            Product(2, "Product 2", "Description 2", 20.0, 0)
        )

        composeTestRule.setContent {
            ProductListScreen(
                products = mockProducts
            )
        }

        composeTestRule.onNodeWithText("Product 1").assertExists()
        composeTestRule.onNodeWithText("Product 2").assertExists()
    }

}
