package com.example.milsaborescompose.viewmodel

import com.example.milsaborescompose.data.model.Product
import com.example.milsaborescompose.data.model.ProductType
import com.example.milsaborescompose.data.repository.ProductRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest : StringSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "getProductsByCategory debe actualizar el uiState con la lista de productos" {
        // 1. Arrange: Preparar datos y mocks
        val categoryId = 1L
        val fakeProductType = ProductType(categoryId, "circular")
        val fakeProducts = listOf(
            Product(
                1L,
                "Torta de Chocolate",
                "Deliciosa torta de chocolate",
                10000,
                fakeProductType
            ),
            Product(2L, "Torta de Fresa", "Torta con fresas frescas", 12000, fakeProductType)
        )

        val mockProductRepository = mockk<ProductRepository>()
        coEvery { mockProductRepository.getProductsByCategory(categoryId) } returns fakeProducts

        // 2. Act: Crear el ViewModel y llamar a la función
        val viewModel = ProductViewModel(mockProductRepository)
        viewModel.getProductsByCategory(categoryId)

        // Avanzar el dispatcher para que se ejecuten las corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // 3. Assert: Comprobar el estado final
        val finalState = viewModel.uiState.value
        finalState.products shouldBe fakeProducts
        finalState.isLoading shouldBe false
        finalState.error shouldBe null
    }
})