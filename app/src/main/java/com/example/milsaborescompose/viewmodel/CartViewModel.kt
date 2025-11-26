package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.model.CartItem
import com.example.milsaborescompose.data.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val allCartItems: StateFlow<List<CartItem>> = repository.allCartItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.addToCart(cartItem)
        }
    }

    fun updateCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.updateCart(cartItem)
        }
    }

    fun deleteFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.deleteFromCart(cartItem)
        }
    }
}
