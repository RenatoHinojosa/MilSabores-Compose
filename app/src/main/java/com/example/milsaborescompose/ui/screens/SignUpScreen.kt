package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.milsaborescompose.data.model.PaymentMethod
import com.example.milsaborescompose.data.model.auth.RegisterRequest
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.PaymentMethodUiState
import com.example.milsaborescompose.viewmodel.UserUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    userUiState: UserUiState,
    paymentMethodUiState: PaymentMethodUiState,
    onRegister: (RegisterRequest) -> Unit,
    onRegistrationSuccess: () -> Unit,
    onErrorDismiss: () -> Unit,
    navController: NavController
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var isPaymentMethodMenuExpanded by remember { mutableStateOf(false) }
    var formError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val isLoading = userUiState.isLoading || paymentMethodUiState.isLoading

    // === EFECTOS SECUNDARIOS ===
    LaunchedEffect(userUiState.isLoggedIn) {
        if (userUiState.isLoggedIn) {
            onRegistrationSuccess()
        }
    }

    val combinedError = userUiState.registrationError ?: paymentMethodUiState.error ?: formError
    LaunchedEffect(combinedError) {
        combinedError?.let {
            snackbarHostState.showSnackbar(it)
            onErrorDismiss()
            formError = null // Clear local form error
        }
    }

    // === UI ===
    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MilSaboresTopAppBar(
                title = "Registro",
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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // --- Campos de texto ---
                TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading, colors = textFieldColors)
                Spacer(Modifier.height(8.dp))
                TextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading, colors = textFieldColors)
                Spacer(Modifier.height(8.dp))
                TextField(value = contrasena, onValueChange = { contrasena = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), enabled = !isLoading, colors = textFieldColors)
                Spacer(Modifier.height(8.dp))
                TextField(value = confirmarContrasena, onValueChange = { confirmarContrasena = it }, label = { Text("Confirmar Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), enabled = !isLoading, colors = textFieldColors)
                Spacer(Modifier.height(8.dp))
                TextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading, colors = textFieldColors)
                Spacer(Modifier.height(8.dp))
                TextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading, colors = textFieldColors)
                Spacer(Modifier.height(16.dp))

                // --- Selector de Método de Pago ---
                ExposedDropdownMenuBox(
                    expanded = isPaymentMethodMenuExpanded,
                    onExpandedChange = { isPaymentMethodMenuExpanded = !isPaymentMethodMenuExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = selectedPaymentMethod?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Método de Pago") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPaymentMethodMenuExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        enabled = !isLoading,
                        colors = textFieldColors
                    )
                    ExposedDropdownMenu(
                        expanded = isPaymentMethodMenuExpanded,
                        onDismissRequest = { isPaymentMethodMenuExpanded = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        paymentMethodUiState.paymentMethods.forEach { method ->
                            DropdownMenuItem(
                                text = { Text(method.name) },
                                onClick = {
                                    selectedPaymentMethod = method
                                    isPaymentMethodMenuExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))

                // --- Botón de Registro ---
                Button(
                    onClick = {
                        // Regex para validar email
                        val emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$".toRegex()


                        if (nombre.isBlank() || correo.isBlank() || contrasena.isBlank() || selectedPaymentMethod == null) {
                            formError = "Por favor completa todos los campos obligatorios."
                        } else if (!emailRegex.matches(correo)) {
                            formError = "Por favor ingresa un correo electrónico válido."
                        } else if (contrasena.length < 6) {
                            formError = "La contraseña debe tener al menos 6 caracteres."
                        } else if (contrasena != confirmarContrasena) {
                            formError = "Las contraseñas no coinciden."
                        } else {
                            onRegister(
                                RegisterRequest(
                                    name = nombre,
                                    email = correo,
                                    password = contrasena,
                                    number = telefono,
                                    address = direccion,
                                    paymentMethodId = selectedPaymentMethod!!.id
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text("Registrarse")
                }
            }
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
