package com.example.milsaborescompose.repository

import com.example.milsaborescompose.data.model.Product
import com.example.milsaborescompose.data.model.ProductType
import com.example.milsaborescompose.data.remote.ProductService
import com.example.milsaborescompose.data.repository.ProductRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class ProductRepositoryTest : StringSpec({

    "getAllProducts debe retornar una lista de productos simulada" {
        // 1. Arrange: Preparar datos y mocks
        val fakeProductType = ProductType(1L, "circular")
        val fakeProducts = listOf(
            Product(
                1L,
                "Torta de Chocolate",
                "Deliciosa torta de chocolate",
                10000,
                fakeProductType
            ),
            Product(2L, "Torta de Vainilla", "Clásica torta de vainilla", 9000, fakeProductType)
        )

        val mockProductService = mockk<ProductService>()
        coEvery { mockProductService.getAllProducts() } returns fakeProducts

        val repo = ProductRepository(mockProductService)

        // 2. Acción (Act)
        runTest {
            val result = repo.getAllProducts()

            // 3. Aserción (Assert)
            result shouldBe fakeProducts
        }
    }
})