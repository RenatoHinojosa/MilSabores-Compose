package com.example.milsaborescompose.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Catalog : Screen("catalog", "Catálogo", Icons.Default.List)
    object Cart : Screen("cart", "Carrito", Icons.Default.ShoppingCart)
    object Profile : Screen("profile", "Perfil", Icons.Default.Person)
}