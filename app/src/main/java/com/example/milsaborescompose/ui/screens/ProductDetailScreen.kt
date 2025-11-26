package com.example.milsaborescompose.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.milsaborescompose.data.model.CartItem
import com.example.milsaborescompose.data.remote.ProductImageResolver
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.CartViewModel
import com.example.milsaborescompose.viewmodel.ProductViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Long,
    viewModelFactory: ViewModelFactory,
    navController: NavController
) {
    val productViewModel: ProductViewModel = viewModel(factory = viewModelFactory)
    val cartViewModel: CartViewModel = viewModel(factory = viewModelFactory)
    val uiState by productViewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        productViewModel.getProductById(productId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MilSaboresTopAppBar(
                title = uiState.selectedProduct?.name ?: "",
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
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
                uiState.error != null -> {
                    Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
                }
                uiState.selectedProduct != null -> {
                    val product = uiState.selectedProduct!!
                    var quantity by remember { mutableStateOf(1) }
                    val imageUrl = ProductImageResolver.getImageUrlForProductId(product.id)

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = product.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                        )

                        Column(modifier = Modifier.padding(24.dp)) {
                            SuggestionChip(
                                onClick = { /* No action */ },
                                label = { Text(product.productType.name.replaceFirstChar { it.uppercase() }) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(product.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }.format(product.price),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Descripción", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(product.description, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Cantidad", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                    Icon(Icons.Filled.Remove, contentDescription = "Disminuir cantidad")
                                }
                                Text(quantity.toString(), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
                                IconButton(onClick = { quantity++ }) {
                                    Icon(Icons.Filled.Add, contentDescription = "Aumentar cantidad")
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = {
                                    val cartItem = CartItem(
                                        productId = product.id.toInt(), // Ensure your CartItem model is consistent
                                        name = product.name,
                                        price = product.price,
                                        image = imageUrl, // Use resolved image URL
                                        quantity = quantity
                                    )
                                    cartViewModel.addToCart(cartItem)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("${product.name} añadido al carrito")
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            ) {
                                Icon(Icons.Filled.ShoppingCart, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                                Text("Añadir al carrito", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
                else -> {
                    Text("Producto no encontrado.")
                }
            }
        }
    }
}
