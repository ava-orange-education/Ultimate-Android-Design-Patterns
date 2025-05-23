package com.example.product_catalog.viewmodel

import com.example.product_catalog.model.Category
import com.example.product_catalog.model.Product
import com.example.product_catalog.model.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
class ProductViewModelTest {

    private val mockRepository = mockk<ProductRepository>()
    private lateinit var viewModel: ProductViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCategories should update categories state`() = runTest {
        val mockCategories = listOf(
            Category(1, "Electronics"),
            Category(2, "Clothing")
        )
        coEvery { mockRepository.fetchCategories() } returns mockCategories

        viewModel.getCategoryList()
        advanceUntilIdle()

        val state = viewModel.categories.value
        assertEquals(2, state.size)
        assertEquals("Electronics", state[0].name)

        println("Mock repository: $mockRepository")
        println("Categories in ViewModel: ${viewModel.categories.value}")

        coVerify { mockRepository.fetchCategories() }
    }

    @Test
    fun `fetchProducts should update products state`() = runTest {
        val mockProducts = listOf(
            Product(1, "Product 1", "Description 1", 10.0, 0),
            Product(2, "Product 2", "Description 2", 20.0, 0)
        )
        coEvery { mockRepository.fetchProducts() } returns mockProducts

        viewModel.getProductList()
        // Advance time to ensure coroutines are executed
        advanceUntilIdle()

        val state = viewModel.products.value
        assertEquals(2, state.size)
        assertEquals("Product 1", state[0].name)

        println("Mock repository: $mockRepository")
        println("Products in ViewModel: ${viewModel.categories.value}")

        coVerify { mockRepository.fetchProducts() }
    }
}
