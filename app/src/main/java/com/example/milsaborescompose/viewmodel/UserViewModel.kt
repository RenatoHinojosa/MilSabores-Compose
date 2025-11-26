package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.model.User
import com.example.milsaborescompose.data.model.auth.LoginRequest
import com.example.milsaborescompose.data.model.auth.RegisterRequest
import com.example.milsaborescompose.data.repository.SessionRepository
import com.example.milsaborescompose.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserUiState(
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val loginError: String? = null,
    val registrationError: String? = null,
    val isLoggedIn: Boolean = false
)

class UserViewModel(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.getSession().collect { session ->
                if (session != null && !_uiState.value.isLoggedIn) {
                    try {
                        _uiState.update { it.copy(isLoading = true) }
                        val user = userRepository.getUserById(session.userId)
                        _uiState.update { it.copy(isLoading = false, currentUser = user, isLoggedIn = true) }
                    } catch (e: Exception) {
                        logout()
                        _uiState.update { it.copy(isLoading = false, loginError = "Failed to restore session.") }
                    }
                } else if (session == null) {
                    _uiState.update { it.copy(currentUser = null, isLoggedIn = false) }
                }
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, loginError = null) }
            try {
                val response = userRepository.login(loginRequest)
                sessionRepository.saveSession(response.id, response.token)
                val user = userRepository.getUserById(response.id) // Get full user details
                 _uiState.update { it.copy(isLoading = false, currentUser = user, isLoggedIn = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, loginError = "Login failed: ${e.message}") }
            }
        }
    }

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, registrationError = null) }
            try {
                val response = userRepository.register(registerRequest)
                sessionRepository.saveSession(response.id, response.token)
                val user = userRepository.getUserById(response.id)
                _uiState.update { it.copy(isLoading = false, currentUser = user, isLoggedIn = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, registrationError = "Registration failed: ${e.message}") }
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val updatedUser = userRepository.updateUser(user.id, user)
                _uiState.update { it.copy(isLoading = false, currentUser = updatedUser) }
            } catch (e: Exception) {
                // Handle update error
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionRepository.clearSession()
        }
    }
    
    fun clearLoginError() {
        _uiState.update { it.copy(loginError = null) }
    }

    fun clearRegistrationError() {
        _uiState.update { it.copy(registrationError = null) }
    }
}