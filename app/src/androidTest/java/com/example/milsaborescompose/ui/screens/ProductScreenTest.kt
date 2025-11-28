package com.example.milsaborescompose.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.milsaborescompose.data.model.Product
import com.example.milsaborescompose.data.model.ProductType
import com.example.milsaborescompose.ui.theme.MilSaboresComposeTheme
import com.example.milsaborescompose.viewmodel.ProductUiState
import com.example.milsaborescompose.viewmodel.ProductViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun productScreen_muestra_lista_de_productos() {
        // 1. Arrange: Preparar datos y mocks
        val fakeProductType = ProductType(1L, "circular")
        val fakeProducts = listOf(
            Product(
                1L,
                "Torta de Manjar",
                "Deliciosa torta de manjar casero",
                15000,
                fakeProductType
            ),
            Product(2L, "Kuchen de Nuez", "Tradicional kuchen de nuez", 12000, fakeProductType)
        )

        val fakeUiState = ProductUiState(
            products = fakeProducts,
            isLoading = false
        )

        // Crear un mock del ViewModel
        val mockViewModel = mockk<ProductViewModel>()

        // Definir el comportamiento del mock: cuando se acceda a uiState, devolver nuestro estado falso
        every { mockViewModel.uiState } returns MutableStateFlow(fakeUiState)

        // Las funciones de acción no necesitan hacer nada en este test, pero deben ser definidas
        every { mockViewModel.getProductsByCategory(any()) } returns Unit
        every { mockViewModel.deleteProduct(any()) } returns Unit

        // 2. Act: Renderizar el Composable con el ViewModel simulado
        composeTestRule.setContent {
            MilSaboresComposeTheme {
                ProductScreen(
                    viewModel = mockViewModel
                )
            }
        }

        // 3. Assert: Verificar que los datos de los productos se muestran en pantalla
        composeTestRule.onNodeWithText("Gestión de Productos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Torta de Manjar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Precio: $15000").assertIsDisplayed()
        composeTestRule.onNodeWithText("Kuchen de Nuez").assertIsDisplayed()
        composeTestRule.onNodeWithText("Precio: $12000").assertIsDisplayed()
    }
}