package com.example.milsaborescompose.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val number: String?,
    val address: String?,
    val paymentMethodId: Long
)
