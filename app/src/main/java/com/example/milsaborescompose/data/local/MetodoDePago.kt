package com.example.milsaborescompose.data.local

enum class MetodoDePago(val displayName: String) {
    TARJETA_DE_CREDITO("Tarjeta de crédito"),
    TARJETA_DE_DEBITO("Tarjeta de débito"),
    TRANSFERENCIA_BANCARIA("Transferencia bancaria"),
    PAYPAL("PayPal"),
    EFECTIVO("Efectivo")
}