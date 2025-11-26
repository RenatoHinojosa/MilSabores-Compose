package com.example.milsaborescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val productId: Int,
    val name: String,
    val price: Int,
    val image: String,
    var quantity: Int
)
