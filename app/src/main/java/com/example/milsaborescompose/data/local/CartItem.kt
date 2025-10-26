package com.example.milsaborescompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val price: Int,
    val image: String,
    var quantity: Int
)
