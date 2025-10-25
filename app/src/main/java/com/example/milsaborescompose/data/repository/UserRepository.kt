package com.example.milsaborescompose.data.repository

import com.example.milsaborescompose.data.local.User
import  com.example.milsaborescompose.data.local.UserDao
import kotlinx.coroutines.flow.Flow
class UserRepository(private val dao: UserDao) {
    val users: Flow<List<User>> = dao.getAllUsers()
    suspend fun insert(user: User) = dao.insertUser(user)
    suspend fun update(user: User) = dao.updateUser(user)
    suspend fun delete(user: User) = dao.deleteUser(user)
}