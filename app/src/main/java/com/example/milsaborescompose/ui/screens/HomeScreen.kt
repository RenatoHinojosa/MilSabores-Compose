package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // Scaffold es un layout que provee una estructura básica para la pantalla,
    // incluyendo soporte para TopAppBar, BottomAppBar, FloatingActionButton, etc.
    Scaffold(
        topBar = {
            // TopAppBar muestra una barra de aplicación en la parte superior de la pantalla.
            TopAppBar(
                title = { Text("Mi App Kotlin") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        // Column es un layout que apila sus elementos hijos verticalmente.
        Column(
            modifier = Modifier
                .padding(innerPadding) // Aplica el padding para no superponer con la TopAppBar.
                .fillMaxSize() // Ocupa todo el espacio disponible.
                .padding(16.dp), // Padding adicional para el contenido.
            verticalArrangement = Arrangement.spacedBy(20.dp), // Espaciado uniforme entre los elementos.
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente.
        ) {
            // Elemento de texto que muestra un mensaje de bienvenida.
            Text("¡Bienvenido!")

            // Botón con una acción (a ser implementada en el futuro).
            Button(onClick = { /* accion futura */ }) {
                Text("Presioname")
            }

            // Imagen que muestra el logo de la aplicación.
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo App",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )

            // Nuevo elemento visual: un indicador de progreso circular.
            CircularProgressIndicator()

            // Card con TextField y Slider para demostrar interactividad.
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    var textState by remember { mutableStateOf("") }
                    TextField(
                        value = textState,
                        onValueChange = { textState = it },
                        label = { Text("Introduce texto") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    var sliderPosition by remember { mutableStateOf(0f) }
                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 0f..100f
                    )
                    Text(text = "Valor: ${sliderPosition.toInt()}",
                         modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
