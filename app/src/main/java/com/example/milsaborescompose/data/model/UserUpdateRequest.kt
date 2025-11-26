package com.example.milsaborescompose.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    @SerialName("name")
    val name: String,
    @SerialName("mail")
    val mail: String,
    @SerialName("address")
    val address: String?,
    @SerialName("number")
    val number: String?,
    @SerialName("paymentMethod")
    val paymentMethod: PaymentMethod
)