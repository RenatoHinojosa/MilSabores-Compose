package com.example.milsaborescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.milsaborescompose.ui.screens.HomeScreen
import com.example.milsaborescompose.ui.theme.MilSaboresComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MilSaboresComposeTheme {
                HomeScreen()
            }
        }
    }
}