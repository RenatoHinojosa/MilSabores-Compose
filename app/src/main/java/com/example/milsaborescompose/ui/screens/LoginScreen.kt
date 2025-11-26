package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.milsaborescompose.data.model.auth.LoginRequest
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.UserUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: UserUiState,
    onLogin: (LoginRequest) -> Unit,
    onLoginSuccess: () -> Unit,
    onErrorDismiss: () -> Unit,
    navController: NavController
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    // Reacciona a los cambios de estado del ViewModel
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(uiState.loginError) {
        uiState.loginError?.let { error ->
            snackbarHostState.showSnackbar(message = error)
            onErrorDismiss() // Limpia el error en el ViewModel después de mostrarlo
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MilSaboresTopAppBar(
                title = "Iniciar Sesión",
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    enabled = !uiState.isLoading,
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    enabled = !uiState.isLoading,
                    singleLine = true
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        onLogin(LoginRequest(email = correo, password = contrasena))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                ) {
                    Text("Entrar")
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
