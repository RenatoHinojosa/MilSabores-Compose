package com.example.milsaborescompose

import com.example.milsaborescompose.ui.users.UserScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.milsaborescompose.ui.theme.MilSaboresComposeTheme
import androidx.room.Room
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.milsaborescompose.data.local.AppDatabase
import com.example.milsaborescompose.data.repository.UserRepository
import com.example.milsaborescompose.viewmodel.UserViewModel
import com.example.milsaborescompose.viewmodel.UserViewModelFactory
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()
        val repo = UserRepository(db.userDao())
        val factory = UserViewModelFactory(repo)
        setContent {
            val viewModel: UserViewModel = viewModel(factory = factory)
            MilSaboresComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserScreen(viewModel,
                        Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}