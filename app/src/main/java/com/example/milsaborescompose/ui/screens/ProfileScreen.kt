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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.milsaborescompose.data.model.PaymentMethod
import com.example.milsaborescompose.data.model.User
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.PaymentMethodUiState
import com.example.milsaborescompose.viewmodel.UserUiState
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: UserUiState,
    paymentUiState: PaymentMethodUiState,
    onLogout: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onUpdateUser: (User) -> Unit,
    onSaveProfilePicture: (String) -> Unit,
    navController: NavController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    
    // === EFECTOS SECUNDARIOS ===
    LaunchedEffect(uiState.isLoggedIn, uiState.currentUser) {
        if (!uiState.isLoggedIn && uiState.currentUser == null) {
             // Si no hay usuario logueado, nos quedamos aquí para mostrar las opciones de login/registro
        }
    }

    // Mostrar errores de actualización
    LaunchedEffect(uiState.updateError) {
        uiState.updateError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
    }

    // === UI ===
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                    UserProfileContent(
                        user = uiState.currentUser,
                        profilePictureUri = uiState.profilePictureUri,
                        paymentMethods = paymentUiState.paymentMethods,
                        onLogout = onLogout,
                        onUpdateUser = onUpdateUser,
                        onSaveProfilePicture = onSaveProfilePicture
                    )
                }
                else -> {
                    // Vista para usuario no logueado
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("No has iniciado sesión.", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { navController.navigate("login") }) {
                            Text("Iniciar Sesión")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { navController.navigate("signup") }) {
                            Text("Registrarse")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileContent(
    user: User,
    profilePictureUri: String?,
    paymentMethods: List<PaymentMethod>,
    onLogout: () -> Unit,
    onUpdateUser: (User) -> Unit,
    onSaveProfilePicture: (String) -> Unit
) {
    var isInEditMode by remember { mutableStateOf(false) }
    
    // Estados locales para edición
    var nombre by remember(user) { mutableStateOf(user.name) }
    var correo by remember(user) { mutableStateOf(user.mail) }
    var telefono by remember(user) { mutableStateOf(user.number ?: "") }
    var direccion by remember(user) { mutableStateOf(user.address ?: "") }
    var selectedPaymentMethod by remember(user) { mutableStateOf(user.paymentMethod) }
    
    var expanded by remember { mutableStateOf(false) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launchers para cámara y galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { onSaveProfilePicture(it.toString()) }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempImageUri?.let { onSaveProfilePicture(it.toString()) }
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
        
        if (isInEditMode) {
            Text(
                text = "Toca para cambiar foto", 
                style = MaterialTheme.typography.labelSmall, 
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        AnimatedContent(targetState = isInEditMode, label = "EditModeAnimation") { isEditing ->
            if (isEditing) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    
                    ExposedDropdownMenuBox(
                        expanded = expanded, 
                        onExpandedChange = { expanded = !expanded }, 
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = selectedPaymentMethod.name,
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
                            paymentMethods.forEach { method ->
                                DropdownMenuItem(
                                    text = { Text(method.name) },
                                    onClick = {
                                        selectedPaymentMethod = method
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
                        ProfileInfoRow(icon = Icons.Default.Person, label = "Nombre", value = user.name)
                        ProfileInfoRow(icon = Icons.Default.Email, label = "Correo", value = user.mail)
                        ProfileInfoRow(icon = Icons.Default.Phone, label = "Teléfono", value = user.number ?: "No especificado")
                        ProfileInfoRow(icon = Icons.Default.Person, label = "Dirección", value = user.address ?: "No especificada")
                        ProfileInfoRow(icon = Icons.Default.Star, label = "Método de pago", value = user.paymentMethod.name)
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        if (isInEditMode) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = {
                    // Revertir cambios
                    nombre = user.name
                    correo = user.mail
                    telefono = user.number ?: ""
                    direccion = user.address ?: ""
                    selectedPaymentMethod = user.paymentMethod
                    isInEditMode = false
                }) { Text("Cancelar") }
                
                Spacer(Modifier.width(16.dp))
                
                Button(onClick = {
                    val updatedUser = user.copy(
                        name = nombre,
                        mail = correo,
                        number = telefono,
                        address = direccion,
                        paymentMethod = selectedPaymentMethod
                    )
                    onUpdateUser(updatedUser)
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