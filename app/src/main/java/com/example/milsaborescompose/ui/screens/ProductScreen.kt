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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.data.model.Product
import com.example.milsaborescompose.data.model.ProductType
import com.example.milsaborescompose.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(viewModel: ProductViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }

    // Cargar productos al entrar (puedes ajustar la categoría si es necesario)
    LaunchedEffect(Unit) {
        viewModel.getProductsByCategory(1) // Carga una categoría por defecto o todas
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                productToEdit = null // Asegurarse de que es un producto nuevo
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Producto")
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Text("Gestión de Productos", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(vertical = 16.dp))

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when {
                    uiState.isLoading -> CircularProgressIndicator()
                    uiState.error != null -> Text(uiState.error!!, color = MaterialTheme.colorScheme.error)
                    else -> {
                        LazyColumn {
                            items(uiState.products) { product ->
                                ListItem(
                                    headlineContent = { Text(product.name) },
                                    supportingContent = { Text("Precio: $${product.price}") },
                                    trailingContent = {
                                        Row {
                                            IconButton(onClick = {
                                                productToEdit = product
                                                showDialog = true
                                            }) {
                                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                                            }
                                            IconButton(onClick = { viewModel.deleteProduct(product.id) }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                            }
                                        }
                                    }
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        ProductEditDialog(
            product = productToEdit,
            onDismiss = { showDialog = false },
            onConfirm = {
                if (productToEdit == null) {
                    viewModel.createProduct(it)
                } else {
                    viewModel.updateProduct(it)
                }
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductEditDialog(
    product: Product?,
    onDismiss: () -> Unit,
    onConfirm: (Product) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    // Este es un ejemplo, deberías obtener esta lista de un ViewModel
    val productTypes = listOf(
        ProductType(1, "circular"), ProductType(2, "cuadrada"), ProductType(3, "individual"),
        ProductType(4, "sinazucar"), ProductType(5, "tradicional"), ProductType(6, "singluten"),
        ProductType(7, "vegano"), ProductType(8, "especial")
    )
    var selectedType by remember { mutableStateOf(product?.productType ?: productTypes.first()) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (product == null) "Añadir Producto" else "Editar Producto") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                Spacer(Modifier.height(8.dp))
                TextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
                Spacer(Modifier.height(8.dp))
                TextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
                Spacer(Modifier.height(16.dp))
                ExposedDropdownMenuBox(expanded = isMenuExpanded, onExpandedChange = { isMenuExpanded = !isMenuExpanded }) {
                    TextField(
                        value = selectedType.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMenuExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = isMenuExpanded, onDismissRequest = { isMenuExpanded = false }) {
                        productTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name) },
                                onClick = { selectedType = type; isMenuExpanded = false }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val newProduct = Product(
                    id = product?.id ?: 0,
                    name = name,
                    description = description,
                    price = price.toIntOrNull() ?: 0,
                    productType = selectedType
                )
                onConfirm(newProduct)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
