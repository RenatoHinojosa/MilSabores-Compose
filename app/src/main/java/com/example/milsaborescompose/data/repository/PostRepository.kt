package com.example.milsaborescompose.data.repository

import com.example.milsaborescompose.data.model.Post
import com.example.milsaborescompose.data.remote.RetrofitInstance

//aqui se acceden a los datos usando el retrofit :D
class PostRepository {
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()

    }

}