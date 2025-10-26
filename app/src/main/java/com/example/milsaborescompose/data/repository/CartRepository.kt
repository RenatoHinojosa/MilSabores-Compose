package com.example.milsaborescompose.data.repository

import com.example.milsaborescompose.data.local.CartDao
import com.example.milsaborescompose.data.local.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun addToCart(cartItem: CartItem) {
        val existingItem = cartDao.findItemById(cartItem.productId)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + cartItem.quantity)
            cartDao.updateCart(updatedItem)
        } else {
            cartDao.addToCart(cartItem)
        }
    }

    suspend fun updateCart(cartItem: CartItem) {
        cartDao.updateCart(cartItem)
    }

    suspend fun deleteFromCart(cartItem: CartItem) {
        cartDao.deleteFromCart(cartItem)
    }
}
