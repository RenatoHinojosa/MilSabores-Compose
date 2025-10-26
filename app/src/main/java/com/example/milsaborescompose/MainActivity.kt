package com.example.milsaborescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.milsaborescompose.ui.screens.MainScreen
import com.example.milsaborescompose.ui.theme.MilSaboresComposeTheme
import com.example.milsaborescompose.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val application = application as MilSaboresApplication
        val viewModelFactory = ViewModelFactory(
            application.userRepository, 
            application.productRepository,
            application.cartRepository
        )

        setContent {
            MilSaboresComposeTheme {
                MainScreen(viewModelFactory = viewModelFactory)
            }
        }
    }
}