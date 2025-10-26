package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.CartViewModel
import com.example.milsaborescompose.viewmodel.ProductViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModelFactory: ViewModelFactory,
    navController: NavController
) {
    val productViewModel: ProductViewModel = viewModel(factory = viewModelFactory)
    val cartViewModel: CartViewModel = viewModel(factory = viewModelFactory)
    val product by productViewModel.selectedProduct.collectAsState()

    var quantity by remember { mutableStateOf(1) }

    LaunchedEffect(productId) {
        productViewModel.getProductById(productId)
    }

    Scaffold(
        topBar = {
            MilSaboresTopAppBar(
                title = product?.name ?: "Detalle del Producto",
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        product?.let { p ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = p.image,
                    contentDescription = p.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(p.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(p.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text("$${p.price}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f)) // Pushes content below to the bottom

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Disminuir cantidad")
                    }
                    Text(text = quantity.toString(), style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(horizontal = 16.dp))
                    IconButton(onClick = { quantity++ }) {
                        Icon(Icons.Filled.Add, contentDescription = "Aumentar cantidad")
                    }
                }

                Button(
                    onClick = {
                        val cartItem = CartItem(
                            productId = p.id,
                            name = p.name,
                            price = p.price,
                            image = p.image,
                            quantity = quantity
                        )
                        cartViewModel.addToCart(cartItem)
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Añadir al carrito")
                }
            }
        }
    }
}