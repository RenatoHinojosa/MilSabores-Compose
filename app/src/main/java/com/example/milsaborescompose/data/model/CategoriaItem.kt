package com.example.milsaborescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaItem(
    val nombre: String,
    val categoriaId: Long, // Corresponds to ProductType id
    val imagen: String
)
