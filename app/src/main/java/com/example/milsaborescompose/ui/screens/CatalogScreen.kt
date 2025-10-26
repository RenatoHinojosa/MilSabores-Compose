package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.milsaborescompose.viewmodel.CatalogViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory

@Composable
fun CatalogScreen(
    viewModelFactory: ViewModelFactory,
    onCategoryClick: (String) -> Unit,
    onAddProductClick: () -> Unit
) {
    val viewModel: CatalogViewModel = viewModel(factory = viewModelFactory)
    val categorias by viewModel.categorias.collectAsState()

    if (categorias.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No hay categorías disponibles.", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onAddProductClick, modifier = Modifier.padding(top = 16.dp)) {
                Text("Agregar Productos")
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categorias) { categoria ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onCategoryClick(categoria.categoria) }
                ) {
                    Column {
                        AsyncImage(
                            model = categoria.imagen,
                            contentDescription = categoria.nombre,
                            modifier = Modifier.aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = categoria.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
