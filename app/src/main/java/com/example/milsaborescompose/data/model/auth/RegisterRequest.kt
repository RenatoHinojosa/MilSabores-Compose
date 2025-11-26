package com.example.milsaborescompose.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("number")
    val number: String,
    @SerialName("address")
    val address: String,
    @SerialName("paymentMethodId")
    val paymentMethodId: Long
)