package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.milsaborescompose.ui.components.MilSaboresTopAppBar
import com.example.milsaborescompose.viewmodel.ProductViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryProductsScreen(
    categoryName: String,
    viewModelFactory: ViewModelFactory,
    navController: NavController,
    onProductClick: (Int) -> Unit
) {
    val viewModel: ProductViewModel = viewModel(factory = viewModelFactory)
    val products by viewModel.products.collectAsState()

    val filteredProducts = products.filter { it.category == categoryName }

    Scaffold(
        topBar = {
            MilSaboresTopAppBar(
                title = categoryName.replaceFirstChar { it.uppercase() },
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            items(filteredProducts) { product ->
                Card(
                    onClick = { onProductClick(product.id) },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        AsyncImage(
                            model = product.image,
                            contentDescription = product.name,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(text = product.description, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }.format(product.price),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
