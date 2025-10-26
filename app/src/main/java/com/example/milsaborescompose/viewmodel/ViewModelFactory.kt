package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.milsaborescompose.data.repository.CartRepository
import com.example.milsaborescompose.data.repository.ProductRepository
import com.example.milsaborescompose.data.repository.UserRepository

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(productRepository) as T
        }
        if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatalogViewModel(productRepository) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}