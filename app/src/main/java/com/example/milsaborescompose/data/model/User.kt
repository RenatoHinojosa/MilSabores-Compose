package com.example.milsaborescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = 0,
    val name: String,
    val mail: String,
    val password: String,
    val address: String?,
    val number: String?,
    val paymentMethod: PaymentMethod
)
