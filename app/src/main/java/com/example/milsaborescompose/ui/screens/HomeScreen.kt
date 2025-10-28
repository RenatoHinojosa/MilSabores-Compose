package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.R
import com.example.milsaborescompose.ui.theme.MilSaboresComposeTheme

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Header Section with centered Logo
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Pastelería 1000 Sabores",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Pastelería 1000 Sabores",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Celebrando 50 años de tradición y sabor en Chile. Desde 1974, hemos endulzado los momentos más especiales de nuestros clientes.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // Guinness Record Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Récord Guinness",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Récord Guinness 1995",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Formamos parte de la historia al participar en la creación de la torta más grande del mundo, un logro que refleja nuestro compromiso con la excelencia.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // Services Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Nuestros Servicios",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ServiceItem(
                    icon = { 
                        Icon(
                            painter = painterResource(id = R.drawable.delivery),
                            contentDescription = "Delivery",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                     },
                    title = "Delivery",
                    description = "Entregamos tus productos favoritos directamente en tu hogar con la frescura y calidad que nos caracteriza."
                )
                ServiceItem(
                    icon = { 
                        Icon(
                            painter = painterResource(id = R.drawable.cake),
                            contentDescription = "Pedidos Especiales",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                     },
                    title = "Pedidos Especiales",
                    description = "Creamos tortas personalizadas para tus celebraciones más importantes con diseños únicos y sabores exclusivos."
                )
                ServiceItem(
                    icon = { 
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Atención Personalizada",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                     },
                    title = "Atención Personalizada",
                    description = "Nuestro equipo especializado te asesora para encontrar el producto perfecto para cada ocasión."
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // Testimonials Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Lo que dicen nuestros clientes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        TestimonialCard(
                            quote = "\"Las tortas de 1000 Sabores han sido parte de todas nuestras celebraciones familiares por más de 20 años. ¡Simplemente deliciosas!\"",
                            author = "- María González"
                        )
                    }
                    item {
                        TestimonialCard(
                            quote = "\"La atención es excepcional y los sabores son únicos. Recomiendo especialmente la torta de manjar.\"",
                            author = "- Carlos Rodríguez"
                        )
                    }
                    item {
                        TestimonialCard(
                            quote = "\"Una tradición familiar que pasa de generación en generación. Calidad garantizada siempre.\"",
                            author = "- Ana Morales"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceItem(icon: @Composable () -> Unit, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun TestimonialCard(quote: String, author: String) {
    Card(
        modifier = Modifier.width(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Filled.FormatQuote,
                contentDescription = "Quote",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = quote,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = author,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun HomeScreenPreview() {
    MilSaboresComposeTheme {
        HomeScreen()
    }
}