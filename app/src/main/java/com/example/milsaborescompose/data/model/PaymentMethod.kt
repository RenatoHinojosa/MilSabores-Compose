package com.example.milsaborescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethod(
    val id: Long,
    val name: String
)
