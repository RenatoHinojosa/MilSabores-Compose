package com.example.milsaborescompose.data.remote
import com.example.milsaborescompose.data.model.Post
import retrofit2.http.GET
interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<Post>

}