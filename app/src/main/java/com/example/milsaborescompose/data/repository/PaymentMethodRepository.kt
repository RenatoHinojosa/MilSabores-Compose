package com.example.milsaborescompose.data.repository

import com.example.milsaborescompose.data.model.PaymentMethod
import com.example.milsaborescompose.data.remote.PaymentMethodService

class PaymentMethodRepository(private val paymentMethodService: PaymentMethodService) {

    suspend fun getAllPaymentMethods(): List<PaymentMethod> {
        return paymentMethodService.getAllPaymentMethods()
    }

    suspend fun getPaymentMethodById(id: Long): PaymentMethod {
        return paymentMethodService.getPaymentMethodById(id)
    }

    suspend fun createPaymentMethod(paymentMethod: PaymentMethod): PaymentMethod {
        return paymentMethodService.createPaymentMethod(paymentMethod)
    }

    suspend fun updatePaymentMethod(id: Long, paymentMethod: PaymentMethod): PaymentMethod {
        return paymentMethodService.updatePaymentMethod(id, paymentMethod)
    }

    suspend fun deletePaymentMethod(id: Long) {
        paymentMethodService.deletePaymentMethod(id)
    }
}
