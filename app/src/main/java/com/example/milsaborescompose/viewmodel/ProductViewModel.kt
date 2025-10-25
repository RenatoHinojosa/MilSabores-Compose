package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.local.Product
import com.example.milsaborescompose.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    val products = repository.products.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(),
        emptyList()
    )

    fun addProduct(id: String, name: String, description: String, price: Double, image: String, category: String) {
        viewModelScope.launch {
            repository.insert(Product(id = id, name = name, description = description, price = price, image = image, category = category))
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.delete(product)
        }
    }

    fun updateProduct(product: Product, newName: String, newDescription: String, newPrice: Double, newImage: String, newCategory: String) {
        viewModelScope.launch {
            repository.update(product.copy(name = newName, description = newDescription, price = newPrice, image = newImage, category = newCategory))
        }
    }
}
