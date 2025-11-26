package com.example.milsaborescompose.data.remote

import com.example.milsaborescompose.data.repository.SessionRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitInstance(sessionRepository: SessionRepository) {

    // TODO: Reemplaza esto con la URL base de tu API real
    private val baseUrl = "http://Opasteleria-api-env.eba-ctypcpd8.us-east-1.elasticbeanstalk.com/"

    private val json = Json { ignoreUnknownKeys = true }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(sessionRepository))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

    val paymentMethodService: PaymentMethodService by lazy {
        retrofit.create(PaymentMethodService::class.java)
    }
}