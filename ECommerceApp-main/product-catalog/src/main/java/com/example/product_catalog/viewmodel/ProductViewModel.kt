package com.example.product_catalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product_catalog.model.Category
import com.example.product_catalog.model.Product
import com.example.product_catalog.model.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    var isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        getCategoryList()
        getProductList()
    }

    fun getCategoryList() {
        viewModelScope.launch {
            _categories.value = productRepository.fetchCategories()
        }
    }

    fun getProductList() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(1000)
            _products.value = productRepository.fetchProducts()
            _isRefreshing.value = false
        }
    }

}