package com.example.milsaborescompose.viewmodel

import com.example.milsaborescompose.data.model.CartItem
import com.example.milsaborescompose.data.repository.CartRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class CartViewModelTest : StringSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "agregarAlCarrito debe llamar al método addToCart del repositorio" {
        // Arrange
        val mockRepo = mockk<CartRepository>(relaxed = true)
        // Simulamos el flow del repositorio para que el stateIn del ViewModel funcione
        coEvery { mockRepo.allCartItems } returns MutableStateFlow(emptyList())
        
        val viewModel = CartViewModel(mockRepo)
        val item = CartItem(1, "Torta Milhojas", 15000, "url", 1)

        // Act
        viewModel.addToCart(item)
        testDispatcher.scheduler.advanceUntilIdle() // Ejecutar corrutinas pendientes

        // Assert
        coVerify { mockRepo.addToCart(item) }
    }

    "eliminarDelCarrito debe llamar al método deleteFromCart del repositorio" {
        // Arrange
        val mockRepo = mockk<CartRepository>(relaxed = true)
        coEvery { mockRepo.allCartItems } returns MutableStateFlow(emptyList())
        
        val viewModel = CartViewModel(mockRepo)
        val item = CartItem(1, "Torta Milhojas", 15000, "url", 1)

        // Act
        viewModel.deleteFromCart(item)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { mockRepo.deleteFromCart(item) }
    }

    "actualizarCarrito debe llamar al método updateCart del repositorio" {
        // Arrange
        val mockRepo = mockk<CartRepository>(relaxed = true)
        coEvery { mockRepo.allCartItems } returns MutableStateFlow(emptyList())
        
        val viewModel = CartViewModel(mockRepo)
        val item = CartItem(1, "Torta Milhojas", 15000, "url", 2)

        // Act
        viewModel.updateCart(item)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { mockRepo.updateCart(item) }
    }
})