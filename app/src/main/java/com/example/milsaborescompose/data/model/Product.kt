package com.example.milsaborescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val productType: ProductType
)
