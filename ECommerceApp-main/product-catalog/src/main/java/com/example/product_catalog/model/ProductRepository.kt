package com.example.product_catalog.model

import com.example.core.ApiCategory
import com.example.core.ApiProduct
import com.example.core.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val apiService: ApiService
) {

    suspend fun fetchCategories(): List<Category> {
        return apiService.getCategories()
            .map { it.toDomainModel() }
    }

    suspend fun fetchProducts(): List<Product> {
        val apiProducts = apiService.getProducts()
        return apiProducts.map { it.toDomainModel() }
    }

    suspend fun getProductDetails(productId: Int): Product {
        val apiProducts = apiService.getProducts()
        return apiProducts.find { it.id == productId }?.toDomainModel()
            ?: throw IllegalArgumentException("Product not found")
    }

    fun ApiCategory.toDomainModel(): Category {
        return Category(
            id = this.id,
            name = this.name
        )
    }

    fun ApiProduct.toDomainModel(): Product {
        return Product(
            id = this.id,
            name = this.name,
            price = this.price,
            description = this.description,
            imageId = this.imageId
        )
    }
}
