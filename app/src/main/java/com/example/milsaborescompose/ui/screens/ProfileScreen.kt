package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.UserUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: UserUiState,
    onLogout: () -> Unit,
    onLogoutSuccess: () -> Unit,
    navController: NavController
) {
    // === EFECTOS SECUNDARIOS ===
    LaunchedEffect(uiState.isLoggedIn) {
        if (!uiState.isLoggedIn && uiState.currentUser == null) {
            onLogoutSuccess()
        }
    }

    // === UI ===
    Scaffold(
        topBar = {
            MilSaboresTopAppBar(
                title = "Mi Perfil",
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
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
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.currentUser != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Información del Usuario", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        ProfileInfoRow(label = "Nombre:", value = uiState.currentUser.name)
                        ProfileInfoRow(label = "Correo:", value = uiState.currentUser.mail)
                        ProfileInfoRow(label = "Teléfono:", value = uiState.currentUser.number ?: "No especificado")
                        ProfileInfoRow(label = "Dirección:", value = uiState.currentUser.address ?: "No especificada")
                        ProfileInfoRow(label = "Método de Pago:", value = uiState.currentUser.paymentMethod.name)
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Button(onClick = onLogout) {
                            Text("Cerrar Sesión")
                        }
                    }
                }
                else -> {
                    // Este estado se muestra si no hay usuario y no está cargando (ej. sesión expirada)
                    Text("No hay sesión activa.", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label ",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(120.dp)
        )
        Text(text = value, fontSize = 16.sp)
    }
}
