package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.milsaborescompose.R
import com.example.milsaborescompose.ui.theme.MilSaboresComposeTheme

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Hero Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Pastelería 1000 Sabores",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                startY = 300f
                            )
                        )
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Pastelería 1000 Sabores",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Celebrando 50 años de tradición y sabor en Chile.",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // Guinness Record Card
        item {
            SectionCard(
                title = "Récord Guinness 1995",
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Récord Guinness",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                Text(
                    text = "Formamos parte de la historia al participar en la creación de la torta más grande del mundo, un logro que refleja nuestro compromiso con la excelencia.",
                    style = MaterialTheme.typography.bodyMedium,
                )
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
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                ServiceItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.delivery),
                            contentDescription = "Delivery",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    title = "Delivery",
                    description = "Entregamos tus productos favoritos directamente en tu hogar."
                )
                Spacer(modifier = Modifier.height(16.dp))
                ServiceItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.cake),
                            contentDescription = "Pedidos Especiales",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    title = "Pedidos Especiales",
                    description = "Creamos tortas personalizadas para tus celebraciones."
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
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        TestimonialCard(
                            quote = "\"Las tortas de 1000 Sabores son parte de nuestras celebraciones. ¡Deliciosas!\"",
                            author = "- María González"
                        )
                    }
                    item {
                        TestimonialCard(
                            quote = "\"La atención es excepcional y los sabores son únicos. Recomiendo la torta de manjar.\"",
                            author = "- Carlos Rodríguez"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon()
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun ServiceItem(icon: @Composable () -> Unit, title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun TestimonialCard(quote: String, author: String) {
    Card(
        modifier = Modifier.width(300.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Icon(
                imageVector = Icons.Filled.FormatQuote,
                contentDescription = "Quote",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = quote,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.height(80.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = author,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
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