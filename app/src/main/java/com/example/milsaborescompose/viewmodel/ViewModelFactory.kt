package com.example.milsaborescompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.milsaborescompose.data.remote.RetrofitInstance
import com.example.milsaborescompose.data.repository.CartRepository
import com.example.milsaborescompose.data.repository.PaymentMethodRepository
import com.example.milsaborescompose.data.repository.ProductRepository
import com.example.milsaborescompose.data.repository.SessionRepository
import com.example.milsaborescompose.data.repository.UserRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val sessionRepository by lazy { SessionRepository(context.applicationContext) }
    private val retrofitInstance by lazy { RetrofitInstance(sessionRepository) }
    private val userRepository by lazy { UserRepository(retrofitInstance.userService) }
    private val productRepository by lazy { ProductRepository(retrofitInstance.productService) }
    private val cartRepository by lazy { CartRepository(context.applicationContext) }
    private val paymentMethodRepository by lazy { PaymentMethodRepository(retrofitInstance.paymentMethodService) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(userRepository, sessionRepository) as T
            }
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(productRepository) as T
            }
            modelClass.isAssignableFrom(CatalogViewModel::class.java) -> {
                CatalogViewModel(productRepository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(cartRepository) as T
            }
            modelClass.isAssignableFrom(PaymentMethodViewModel::class.java) -> {
                PaymentMethodViewModel(paymentMethodRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}