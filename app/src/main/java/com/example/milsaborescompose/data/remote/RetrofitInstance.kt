package com.example.milsaborescompose.data.remote

import retrofit2.Retrofit

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/") //URL base de la api
            .build()
            .create(ApiService::class.java)
    }
}