package com.example.milsaborescompose.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.milsaborescompose.viewmodel.ViewModelFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pantallaCarrito_muestraEstadoVacio_cuandoNoHayProductos() {
        // Arrange: Obtenemos el contexto y creamos el factory real
        // Nota: Esto usará el DataStore real del dispositivo de prueba, que asumimos está vacío o limpiamos
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val viewModelFactory = ViewModelFactory(context)

        // Act: Cargamos la pantalla
        composeTestRule.setContent {
            // Usamos un NavController de prueba y el factory real
            CartScreen(
                navController = androidx.navigation.compose.rememberNavController(),
                viewModelFactory = viewModelFactory
            )
        }

        // Assert: Verificamos que aparezca el texto de carrito vacío
        composeTestRule.onNodeWithText("Tu carrito está vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("Añade productos para verlos aquí.").assertIsDisplayed()
    }
}