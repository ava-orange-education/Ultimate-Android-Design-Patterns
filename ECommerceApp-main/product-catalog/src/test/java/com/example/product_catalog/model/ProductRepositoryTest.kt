package com.example.product_catalog.model

import com.example.core.ApiCategory
import com.example.core.ApiProduct
import com.example.core.ApiService
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryTest {

    private val mockApiService = mockk<ApiService>()
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        productRepository = ProductRepository(mockApiService)
    }

    @Test
    fun `fetchCategories should return domain categories`() = runTest {
        val mockApiCategories = listOf(
            ApiCategory(1, "Electronics"),
            ApiCategory(2, "Clothing")
        )
        coEvery { mockApiService.getCategories() } returns mockApiCategories

        val result = productRepository.fetchCategories()

        assertEquals(2, result.size)
        assertEquals("Electronics", result[0].name)
    }

    @Test
    fun `fetchProducts should return mapped products`() = runTest {
        val mockApiProducts = listOf(
            ApiProduct(1, "Product 1", "Description 1", 10.0, 0),
            ApiProduct(2, "Product 2", "Description 2", 20.0, 0)
        )
        coEvery { mockApiService.getProducts() } returns mockApiProducts

        val result = productRepository.fetchProducts()

        assertEquals(2, result.size)
        assertEquals("Product 1", result[0].name)
        assertEquals(10.0, result[0].price, 0.01)
    }
}
