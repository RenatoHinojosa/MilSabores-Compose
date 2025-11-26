package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.milsaborescompose.data.remote.ProductImageResolver
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.ProductViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryProductsScreen(
    categoryName: String,
    categoryId: Long,
    viewModelFactory: ViewModelFactory,
    navController: NavController,
    onProductClick: (Long) -> Unit
) {
    val viewModel: ProductViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(categoryId) {
        viewModel.getProductsByCategory(categoryId)
    }

    Scaffold(
        topBar = {
            MilSaboresTopAppBar(
                title = categoryName,
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
                uiState.error != null -> {
                    Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
                }
                uiState.products.isEmpty() -> {
                    Text("No hay productos en esta categoría.", style = MaterialTheme.typography.bodyLarge)
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.products) { product ->
                            ProductCard(
                                imageUrl = ProductImageResolver.getImageUrlForProductId(product.id),
                                name = product.name,
                                description = product.description,
                                price = product.price,
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    imageUrl: String,
    name: String,
    description: String,
    price: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ListItem(
            headlineContent = { Text(name, fontWeight = FontWeight.Bold) },
            supportingContent = { Text(description, maxLines = 2) },
            leadingContent = {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = name,
                    modifier = Modifier.clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop
                )
            },
            trailingContent = { Text("$$price", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary) }
        )
    }
}
