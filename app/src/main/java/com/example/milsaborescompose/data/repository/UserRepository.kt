package com.example.milsaborescompose.data.repository

import com.example.milsaborescompose.data.model.User
import com.example.milsaborescompose.data.model.auth.LoginRequest
import com.example.milsaborescompose.data.model.auth.LoginResponse
import com.example.milsaborescompose.data.model.auth.RegisterRequest
import com.example.milsaborescompose.data.remote.UserService

class UserRepository(private val userService: UserService) {

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return userService.login(loginRequest)
    }

    suspend fun register(registerRequest: RegisterRequest): LoginResponse {
        return userService.register(registerRequest)
    }

    suspend fun getUserById(id: Long): User {
        return userService.getUserById(id)
    }

    suspend fun updateUser(id: Long, user: User): User {
        return userService.updateUser(id, user)
    }
}
