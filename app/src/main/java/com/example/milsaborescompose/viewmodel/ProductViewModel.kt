package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.model.Product
import com.example.milsaborescompose.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProductUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val selectedProduct: Product? = null,
    val error: String? = null
)

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun getProductsByCategory(categoryId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val productList = repository.getProductsByCategory(categoryId)
                _uiState.update { it.copy(isLoading = false, products = productList) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load products: ${e.message}") }
            }
        }
    }

    fun getProductById(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, selectedProduct = null) }
            try {
                val product = repository.getProductById(id)
                _uiState.update { it.copy(isLoading = false, selectedProduct = product) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load product details: ${e.message}") }
            }
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.createProduct(product)
                // Optionally, refresh the product list after creation
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to create product: ${e.message}") }
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(product.id, product)
                 _uiState.update { it.copy(selectedProduct = product) } // Optimistic update
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update product: ${e.message}") }
            }
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(id)
                // Optionally, refresh the product list or navigate away
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to delete product: ${e.message}") }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}