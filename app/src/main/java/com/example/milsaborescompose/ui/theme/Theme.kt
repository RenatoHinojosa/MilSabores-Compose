package com.example.milsaborescompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8B4513),           // Chocolate
    secondary = Color(0xFFFFC0CB),         // Rosa Suave
    tertiary = Color(0xFFD2B48C),          // Tan (complementario)
    background = Color(0xFFE1C28F),         // Crema Pastel (fondo principal)
    surface = cardBackground,           // Crema ligeramente más oscuro para Cards y NavBar
    onPrimary = Color.White,               // Texto sobre color primario
    onSecondary = Color(0xFF5D4037),         // Texto sobre color secundario (Marrón Oscuro)
    onTertiary = Color.White,              // Texto sobre color terciario
    onBackground = Color(0xFF5D4037),        // Texto principal (Marrón Oscuro)
    onSurface = Color(0xFF5D4037),         // Texto principal (Marrón Oscuro)
    surfaceVariant = Color(0xFFF0E5CF),     // Variante de superficie (un tono más oscuro)
    onSurfaceVariant = Color(0xFFFFFFFF),   // Texto secundario (Marrón suave, reemplaza el gris)
    outline = Color(0xFFD3C5B4),           // Bordes y divisores (Tan suave, reemplaza el gris)
    error = Color(0xFFB00020),             // Rojo de error estándar
    onError = Color.White,                  // Texto sobre color de error
    surfaceTint = Color(0xFFF8EDD8)        // Se establece igual que surface para anular la elevación tonal
)

@Composable
fun MilSaboresComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography, // Asegúrate de que Typography.kt esté configurado con Lato y Pacifico
        content = content
    )
}
