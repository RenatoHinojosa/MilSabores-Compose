package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.data.local.Product
import com.example.milsaborescompose.viewmodel.ProductViewModel

@Composable
fun ProductScreen(viewModel: ProductViewModel, modifier: Modifier = Modifier) {
    val products by viewModel.products.collectAsState(initial = emptyList())
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    var showEditDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }
    var newName by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var newPrice by remember { mutableStateOf("") }
    var newImage by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Productos", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (name.isNotBlank() && description.isNotBlank() && price.isNotBlank() && image.isNotBlank() && category.isNotBlank()) {
                    viewModel.addProduct(name, description, price.toInt(), image, category)
                    name = ""
                    description = ""
                    price = ""
                    image = ""
                    category = ""
                }
            }) {
                Text("Agregar")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(products) { product ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${product.name} (${product.price})")
                    Row {
                        IconButton(onClick = {
                            productToEdit = product
                            newName = product.name
                            newDescription = product.description
                            newPrice = product.price.toString()
                            newImage = product.image
                            newCategory = product.category
                            showEditDialog = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                        IconButton(onClick = { viewModel.deleteProduct(product) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }

    if (showEditDialog && productToEdit != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    productToEdit?.let {
                        viewModel.updateProduct(it, newName, newDescription, newPrice.toInt(), newImage, newCategory)
                    }
                    showEditDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Editar producto") },
            text = {
                Column {
                    TextField(value = newName, onValueChange = { newName = it }, label = { Text("Nombre") })
                    TextField(value = newDescription, onValueChange = { newDescription = it }, label = { Text("Descripción") })
                    TextField(value = newPrice, onValueChange = { newPrice = it }, label = { Text("Precio") })
                    TextField(value = newImage, onValueChange = { newImage = it }, label = { Text("Imagen") })
                    TextField(value = newCategory, onValueChange = { newCategory = it }, label = { Text("Categoría") })
                }
            }
        )
    }
}