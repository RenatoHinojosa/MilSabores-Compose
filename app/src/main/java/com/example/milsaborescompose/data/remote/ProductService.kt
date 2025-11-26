package com.example.milsaborescompose.data.remote

import com.example.milsaborescompose.data.model.Product
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("api/product")
    suspend fun getAllProducts(): List<Product>

    @GET("api/product/{id}")
    suspend fun getProductById(@Path("id") id: Long): Product

    @GET("api/product/category/{categoryId}")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId: Long): List<Product>

    @POST("api/product")
    suspend fun createProduct(@Body product: Product): Product

    @PUT("api/product/{id}")
    suspend fun updateProduct(@Path("id") id: Long, @Body product: Product): Product

    @DELETE("api/product/{id}")
    suspend fun deleteProduct(@Path("id") id: Long)
}
