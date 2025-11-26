package com.example.milsaborescompose.data.model.auth

import com.example.milsaborescompose.data.model.PaymentMethod
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: Long,
    val token: String,
    val type: String = "Bearer",
    val email: String,
    val name: String,
    val number: String?,
    val address: String?,
    val paymentMethod: PaymentMethod
)
