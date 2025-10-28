package com.example.milsaborescompose.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.data.local.MetodoDePago

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUp: (String, String, String, String, MetodoDePago) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedMetodoDePago by remember { mutableStateOf(MetodoDePago.EFECTIVO) }

    var nombreError by remember { mutableStateOf(false) }
    var correoError by remember { mutableStateOf(false) }
    var contrasenaError by remember { mutableStateOf(false) }

    val isFormValid = !nombreError && !correoError && !contrasenaError && nombre.isNotBlank() && correo.isNotBlank() && contrasena.isNotBlank() && confirmarContrasena.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear una cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        TextField(
            value = nombre,
            onValueChange = { 
                nombre = it
                nombreError = it.isBlank()
            },
            label = { Text("Nombre") },
            isError = nombreError,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = nombreError) {
            Text("El nombre no puede estar vacío", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(8.dp))
        TextField(
            value = correo,
            onValueChange = { 
                correo = it
                correoError = !it.contains("@") || !it.contains(".")
            },
            label = { Text("Correo electrónico") },
            isError = correoError,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = correoError) {
            Text("El correo debe ser válido", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(8.dp))
        TextField(
            value = contrasena,
            onValueChange = { 
                contrasena = it
                contrasenaError = it != confirmarContrasena
            },
            label = { Text("Contraseña") },
            isError = contrasenaError,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = confirmarContrasena,
            onValueChange = { 
                confirmarContrasena = it
                contrasenaError = it != contrasena
            },
            label = { Text("Confirmar contraseña") },
            isError = contrasenaError,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = contrasenaError) {
            Text("Las contraseñas no coinciden", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(8.dp))
        TextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedMetodoDePago.displayName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                MetodoDePago.values().forEach { metodo ->
                    DropdownMenuItem(
                        text = { Text(metodo.displayName) },
                        onClick = {
                            selectedMetodoDePago = metodo
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                onSignUp(nombre, correo, contrasena, telefono, selectedMetodoDePago)
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}