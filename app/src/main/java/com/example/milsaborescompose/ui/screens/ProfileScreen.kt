package com.example.milsaborescompose.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.data.local.MetodoDePago
import com.example.milsaborescompose.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userViewModel: UserViewModel, onLogout: () -> Unit) {
    val users by userViewModel.users.collectAsState()
    val loggedInUserId by userViewModel.loggedInUserId.collectAsState()

    val currentUser = loggedInUserId?.let { userId -> users.find { it.id == userId } }

    var isInEditMode by remember { mutableStateOf(false) }

    // State for editable fields
    var nombre by remember(currentUser) { mutableStateOf(currentUser?.nombre ?: "") }
    var correo by remember(currentUser) { mutableStateOf(currentUser?.correo ?: "") }
    var telefono by remember(currentUser) { mutableStateOf(currentUser?.telefono ?: "") }
    var selectedMetodoDePago by remember(currentUser) { mutableStateOf(currentUser?.metodoDePago ?: MetodoDePago.EFECTIVO) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(24.dp))

        // Profile Picture Placeholder
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto de perfil",
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(24.dp))

        AnimatedContent(targetState = isInEditMode, label = "EditModeAnimation") { isEditing ->
            if (isEditing) {
                // --- EDIT MODE ---
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    TextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo electrónico") }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    TextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(16.dp))
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = selectedMetodoDePago.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Método de pago") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
                }
            } else {
                // --- DISPLAY MODE ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        currentUser?.let {
                            ProfileInfoRow(icon = Icons.Default.Person, label = "Nombre", value = it.nombre)
                            ProfileInfoRow(icon = Icons.Default.Email, label = "Correo", value = it.correo)
                            ProfileInfoRow(icon = Icons.Default.Phone, label = "Teléfono", value = it.telefono)
                            ProfileInfoRow(icon = Icons.Default.Star, label = "Método de pago", value = it.metodoDePago.displayName)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        // --- ACTION BUTTONS ---
        if (isInEditMode) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = {
                    currentUser?.let {
                        nombre = it.nombre
                        correo = it.correo
                        telefono = it.telefono
                        selectedMetodoDePago = it.metodoDePago
                    }
                    isInEditMode = false
                }) { Text("Cancelar") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = {
                    currentUser?.let {
                        val updatedUser = it.copy(nombre = nombre, correo = correo, telefono = telefono, metodoDePago = selectedMetodoDePago)
                        userViewModel.updateUser(updatedUser)
                    }
                    isInEditMode = false
                }) { Text("Guardar") }
            }
        } else {
            Button(onClick = { isInEditMode = true }) { Text("Editar Perfil") }
        }

        Spacer(Modifier.weight(1f))
        OutlinedButton(onClick = onLogout) { Text("Cerrar sesión") }
    }
}

@Composable
private fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}