package com.example.milsaborescompose.data.remote

import com.example.milsaborescompose.data.model.PaymentMethod
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PaymentMethodService {
    @GET("api/payment_method")
    suspend fun getAllPaymentMethods(): List<PaymentMethod>

    @GET("api/payment_method/{id}")
    suspend fun getPaymentMethodById(@Path("id") id: Long): PaymentMethod

    @POST("api/payment_method")
    suspend fun createPaymentMethod(@Body method: PaymentMethod): PaymentMethod

    @PUT("api/payment_method/{id}")
    suspend fun updatePaymentMethod(@Path("id") id: Long, @Body method: PaymentMethod): PaymentMethod

    @DELETE("api/payment_method/{id}")
    suspend fun deletePaymentMethod(@Path("id") id: Long)
}
