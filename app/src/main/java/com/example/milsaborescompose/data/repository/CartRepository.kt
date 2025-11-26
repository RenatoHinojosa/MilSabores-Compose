package com.example.milsaborescompose.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.milsaborescompose.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart")

class CartRepository(private val context: Context) {

    private val CART_KEY = stringPreferencesKey("cart_items")

    val allCartItems: Flow<List<CartItem>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[CART_KEY]
            if (jsonString != null) {
                try {
                    Json.decodeFromString<List<CartItem>>(jsonString)
                } catch (e: Exception) {
                    emptyList<CartItem>()
                }
            } else {
                emptyList<CartItem>()
            }
        }

    private suspend fun saveCart(cartItems: List<CartItem>) {
        context.dataStore.edit {
            it[CART_KEY] = Json.encodeToString(cartItems)
        }
    }

    suspend fun addToCart(cartItem: CartItem) {
        val currentItems = allCartItems.first()
        val existingItem = currentItems.find { it.productId == cartItem.productId }
        val newList = if (existingItem != null) {
            currentItems.map {
                if (it.productId == cartItem.productId) {
                    it.copy(quantity = it.quantity + cartItem.quantity)
                } else {
                    it
                }
            }
        } else {
            currentItems + cartItem
        }
        saveCart(newList)
    }

    suspend fun updateCart(cartItem: CartItem) {
        val currentItems = allCartItems.first()
        val newList = currentItems.map {
            if (it.productId == cartItem.productId) {
                cartItem
            } else {
                it
            }
        }
        saveCart(newList)
    }

    suspend fun deleteFromCart(cartItem: CartItem) {
        val currentItems = allCartItems.first()
        val newList = currentItems.filter { it.productId != cartItem.productId }
        saveCart(newList)
    }

    suspend fun clearCart() {
        saveCart(emptyList())
    }
}