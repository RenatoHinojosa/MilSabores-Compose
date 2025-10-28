package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.local.MetodoDePago
import com.example.milsaborescompose.data.local.SessionManager
import com.example.milsaborescompose.data.local.User
import com.example.milsaborescompose.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    val users = repository.users.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val loggedInUserId = sessionManager.getUserId().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun addUser(nombre: String, correo: String, contrasena: String, telefono: String, metodoDePago: MetodoDePago) {
        viewModelScope.launch {
            repository.insert(User(nombre = nombre, correo = correo, contrasena = contrasena, telefono = telefono, metodoDePago = metodoDePago))
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.delete(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.update(user)
        }
    }

    fun login(correo: String, contrasena: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.users.first().find { it.correo == correo && it.contrasena == contrasena }
            if (user != null) {
                sessionManager.saveSession(user.id)
                onLoginResult(true)
            } else {
                onLoginResult(false)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }
}