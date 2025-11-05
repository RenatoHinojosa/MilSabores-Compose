package com.example.milsaborescompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Chocolate,
    onPrimary = Color.White,
    secondary = SoftPink,
    onSecondary = DarkBrown,
    tertiary = Tan,
    onTertiary = DarkBrown,
    background = CreamPastel,
    onBackground = DarkBrown,
    surface = CardSurface,
    onSurface = DarkBrown,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = OnSurfaceVariantText,
    outline = OutlineColor,
    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun MilSaboresComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
