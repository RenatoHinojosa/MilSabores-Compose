package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.milsaborescompose.data.local.CartItem
import com.example.milsaborescompose.viewmodel.CartViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    navController: NavController,
    viewModelFactory: ViewModelFactory
) {
    val viewModel: CartViewModel = viewModel(factory = viewModelFactory)
    val cartItems by viewModel.allCartItems.collectAsState()
    val totalPrice = cartItems.sumOf { it.price * it.quantity }

    Scaffold(
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                BottomAppBar(
                    modifier = Modifier.height(80.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total: ${NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }.format(totalPrice)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = { /* TODO: Navigate to checkout */ }) {
                            Text("Pagar")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(cartItems, key = { it.productId }) { item ->
                        CartItemRow(
                            item = item,
                            onIncrease = { viewModel.updateCart(item.copy(quantity = item.quantity + 1)) },
                            onDecrease = {
                                if (item.quantity > 1) {
                                    viewModel.updateCart(item.copy(quantity = item.quantity - 1))
                                } else {
                                    viewModel.deleteFromCart(item)
                                }
                            },
                            onDelete = { viewModel.deleteFromCart(item) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = item.name,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }.format(item.price), style = MaterialTheme.typography.bodyMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Default.Delete, contentDescription = "Disminuir cantidad")
                }
                Text(item.quantity.toString(), style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.Add, contentDescription = "Aumentar cantidad")
                }
            }
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Outlined.Delete, contentDescription = "Eliminar del carrito")
        }
    }
}