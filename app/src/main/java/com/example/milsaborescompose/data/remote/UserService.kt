package com.example.milsaborescompose.data.remote

import com.example.milsaborescompose.data.model.User
import com.example.milsaborescompose.data.model.auth.LoginRequest
import com.example.milsaborescompose.data.model.auth.LoginResponse
import com.example.milsaborescompose.data.model.auth.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): LoginResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): User

    @PUT("api/users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): User
}
