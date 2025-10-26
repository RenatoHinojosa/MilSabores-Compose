package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.milsaborescompose.data.local.CartItem
import com.example.milsaborescompose.viewmodel.CartViewModel
import com.example.milsaborescompose.viewmodel.ProductViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModelFactory: ViewModelFactory,
    navController: NavController
) {
    val productViewModel: ProductViewModel = viewModel(factory = viewModelFactory)
    val cartViewModel: CartViewModel = viewModel(factory = viewModelFactory)
    val product by productViewModel.selectedProduct.collectAsState()

    LaunchedEffect(productId) {
        productViewModel.getProductById(productId)
    }

    product?.let { p ->
        val scrollState = rememberScrollState()
        var quantity by remember { mutableStateOf(1) }

        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            AsyncImage(
                model = p.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )
            // Gradient Overlay for content legibility
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent)
                        )
                    )
            )

            // Content Card
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(300.dp)) // Space for the image
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        // Product Name
                        Text(
                            text = p.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Category Chip
                        SuggestionChip(
                            onClick = { /* No action */ },
                            label = {
                                Text(p.category.replaceFirstChar { it.uppercase() })
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Price
                        Text(
                            text = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }.format(p.price),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Description
                        Text("Descripción", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(p.description, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(24.dp))

                        // Quantity Selector
                        Text("Cantidad", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Disminuir cantidad")
                            }
                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            IconButton(onClick = { quantity++ }) {
                                Icon(Icons.Filled.Add, contentDescription = "Aumentar cantidad")
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))

                        // Add to Cart Button
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
                                .height(56.dp)
                        ) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                            Text("Añadir al carrito", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            // Top Back Button - MOVED HERE TO BE ON TOP
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    } ?: run {
        // Loading or error state
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
