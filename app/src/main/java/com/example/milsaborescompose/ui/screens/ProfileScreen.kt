package com.example.milsaborescompose.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.milsaborescompose.data.local.MetodoDePago
import com.example.milsaborescompose.viewmodel.UserViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userViewModel: UserViewModel, onLogout: () -> Unit) {
    val users by userViewModel.users.collectAsState()
    val loggedInUserId by userViewModel.loggedInUserId.collectAsState()

    val currentUser = loggedInUserId?.let { userId -> users.find { it.id == userId } }

    var isInEditMode by remember { mutableStateOf(false) }

    var nombre by remember(currentUser) { mutableStateOf(currentUser?.nombre ?: "") }
    var correo by remember(currentUser) { mutableStateOf(currentUser?.correo ?: "") }
    var telefono by remember(currentUser) { mutableStateOf(currentUser?.telefono ?: "") }
    var selectedMetodoDePago by remember(currentUser) { mutableStateOf(currentUser?.metodoDePago ?: MetodoDePago.EFECTIVO) }
    var profilePictureUri by remember(currentUser) { mutableStateOf(currentUser?.profilePictureUri) }
    var expanded by remember { mutableStateOf(false) }
    var showImageSourceDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { profilePictureUri = it.toString() }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempImageUri?.let { profilePictureUri = it.toString() }
            }
        }
    )

    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val newImageUri = createImageFile(context)
            tempImageUri = newImageUri
            cameraLauncher.launch(newImageUri)
        }
    }

    fun launchGallery() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            galleryLauncher.launch("image/*")
        } else {
            galleryPermissionLauncher.launch(permission)
        }
    }

    fun launchCamera() {
        val permission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            val newImageUri = createImageFile(context)
            tempImageUri = newImageUri
            cameraLauncher.launch(newImageUri)
        } else {
            cameraPermissionLauncher.launch(permission)
        }
    }

    if (showImageSourceDialog) {
        ImageSourceDialog(
            onDismissRequest = { showImageSourceDialog = false },
            onTakePhotoClick = {
                showImageSourceDialog = false
                launchCamera()
            },
            onChooseFromGalleryClick = {
                showImageSourceDialog = false
                launchGallery()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(enabled = isInEditMode) {
                    showImageSourceDialog = true
                },
            contentAlignment = Alignment.Center
        ) {
            if (profilePictureUri != null) {
                AsyncImage(
                    model = Uri.parse(profilePictureUri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        AnimatedContent(targetState = isInEditMode, label = "EditModeAnimation") { isEditing ->
            if (isEditing) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { showImageSourceDialog = true }) {
                        Text("Cambiar foto")
                    }
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        value = nombre, 
                        onValueChange = { nombre = it }, 
                        label = { Text("Nombre") }, 
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = correo, 
                        onValueChange = { correo = it }, 
                        label = { Text("Correo electrónico") }, 
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = telefono, 
                        onValueChange = { telefono = it }, 
                        label = { Text("Teléfono") }, 
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = selectedMetodoDePago.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Método de pago") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded, 
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
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
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
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

        if (isInEditMode) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = {                    currentUser?.let {
                        nombre = it.nombre
                        correo = it.correo
                        telefono = it.telefono
                        selectedMetodoDePago = it.metodoDePago
                        profilePictureUri = it.profilePictureUri
                    }
                    isInEditMode = false
                }) { Text("Cancelar") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = {                    currentUser?.let {
                        val updatedUser = it.copy(
                            nombre = nombre,
                            correo = correo,
                            telefono = telefono,
                            metodoDePago = selectedMetodoDePago,
                            profilePictureUri = profilePictureUri
                        )
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

@Composable
private fun ImageSourceDialog(
    onDismissRequest: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onChooseFromGalleryClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Elige una opción") },
        text = { Text("¿Desde dónde quieres obtener la imagen?") },
        confirmButton = {
            TextButton(onClick = onTakePhotoClick) {
                Text("Tomar foto")
            }
        },
        dismissButton = {
            TextButton(onClick = onChooseFromGalleryClick) {
                Text("Galería")
            }
        }
    )
}

private fun createImageFile(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir      /* directory */
    )
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider",
        image
    )
}
