package com.example.milsaborescompose.ui.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.viewmodel.ProductViewModel

@Composable
fun ProductScreen(viewModel: ProductViewModel, modifier: Modifier = Modifier) {
    val products by viewModel.products.collectAsState()

    Column(modifier = modifier) {
        Button(onClick = {
            // Example product, you can create a form to add products
            viewModel.addProduct(
                id = "TE006",
                name = "Torta de Chocolate",
                description = "Deliciosa torta de chocolate con triple capa.",
                price = 55000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "tradicional"
            )
        }) {
            Text("Add Sample Product")
        }

        LazyColumn {
            items(products) { product ->
                Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(text = "Nombre: ${product.name}")
                    Text(text = "Precio: ${product.price}")
                    Text(text = "Categoría: ${product.category}")
                }
            }
        }
    }
}
