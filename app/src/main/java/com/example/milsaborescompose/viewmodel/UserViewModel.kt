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
import retrofit2.HttpException

data class UserUiState(
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val profilePictureUri: String? = null,
    val loginError: String? = null,
    val registrationError: String? = null,
    val updateError: String? = null, // Nuevo campo para errores de actualización
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
                if (_uiState.value.isLoggedIn && _uiState.value.currentUser != null) {
                   if (session == null) {
                       return@collect
                   }
                   if (session.profilePictureUri != _uiState.value.profilePictureUri) {
                       _uiState.update { it.copy(profilePictureUri = session.profilePictureUri) }
                   }
                }

                if (session != null) {
                     val currentProfilePic = session.profilePictureUri
                     
                    if (!_uiState.value.isLoggedIn) {
                        try {
                            _uiState.update { it.copy(isLoading = true) }
                            val user = userRepository.getUserById(session.userId)
                            _uiState.update { it.copy(
                                isLoading = false, 
                                currentUser = user, 
                                isLoggedIn = true,
                                profilePictureUri = currentProfilePic
                            ) }
                        } catch (e: Exception) {
                            _uiState.update { it.copy(isLoading = false, isLoggedIn = false, currentUser = null) }
                        }
                    } else {
                        if (_uiState.value.profilePictureUri != currentProfilePic) {
                             _uiState.update { it.copy(profilePictureUri = currentProfilePic) }
                        }
                    }
                } else if (session == null) {
                    _uiState.update { it.copy(currentUser = null, isLoggedIn = false, profilePictureUri = null) }
                }
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, loginError = null) }
            try {
                val response = userRepository.login(loginRequest)
                
                val user = User(
                    id = response.id,
                    name = response.name,
                    mail = response.email,
                    password = "",
                    address = response.address,
                    number = response.number,
                    paymentMethod = response.paymentMethod
                )
                
                _uiState.update { it.copy(isLoading = false, currentUser = user, isLoggedIn = true) }
                sessionRepository.saveSession(response.id, response.token)
                
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, loginError = "Error al iniciar sesión: ${e.message}") }
            }
        }
    }

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, registrationError = null) }
            try {
                val response = userRepository.register(registerRequest)
                
                val user = User(
                    id = response.id,
                    name = response.name,
                    mail = response.email,
                    password = "",
                    address = response.address,
                    number = response.number,
                    paymentMethod = response.paymentMethod
                )
                
                _uiState.update { it.copy(isLoading = false, currentUser = user, isLoggedIn = true) }
                sessionRepository.saveSession(response.id, response.token)
                
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (e.code() == 400 && errorBody?.contains("El email ya está registrado") == true) {
                    _uiState.update { it.copy(isLoading = false, registrationError = "Correo inválido o ya ingresado") }
                } else {
                    _uiState.update { it.copy(isLoading = false, registrationError = "Error al registrar: ${e.message()}") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, registrationError = "Error al registrar: ${e.message}") }
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, updateError = null) }
            try {
                val updatedUser = userRepository.updateUser(user.id, user)
                _uiState.update { it.copy(isLoading = false, currentUser = updatedUser) }
            } catch (e: Exception) {
                 // Ahora capturamos y mostramos el error
                 _uiState.update { it.copy(isLoading = false, updateError = "Fallo al actualizar: ${e.message}") }
            }
        }
    }

    fun saveProfilePicture(uri: String) {
        viewModelScope.launch {
            sessionRepository.saveProfilePicture(uri)
            _uiState.update { it.copy(profilePictureUri = uri) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(currentUser = null, isLoggedIn = false, profilePictureUri = null) }
            sessionRepository.clearSession()
        }
    }
    
    fun clearLoginError() {
        _uiState.update { it.copy(loginError = null) }
    }

    fun clearRegistrationError() {
        _uiState.update { it.copy(registrationError = null) }
    }
    
    fun clearUpdateError() {
        _uiState.update { it.copy(updateError = null) }
    }
}