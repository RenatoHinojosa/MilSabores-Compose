package com.example.milsaborescompose

import android.app.Application
import com.example.milsaborescompose.data.local.AppDatabase
import com.example.milsaborescompose.data.repository.CartRepository
import com.example.milsaborescompose.data.repository.ProductRepository
import com.example.milsaborescompose.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MilSaboresApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    val cartRepository by lazy { CartRepository(database.cartDao()) }
}