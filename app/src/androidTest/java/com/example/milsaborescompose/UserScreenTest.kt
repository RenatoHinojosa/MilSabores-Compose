package com.example.milsaborescompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.milsaborescompose.data.model.PaymentMethod
import com.example.milsaborescompose.data.model.User
import com.example.milsaborescompose.ui.screens.ProfileScreen
import com.example.milsaborescompose.ui.theme.MilSaboresComposeTheme
import com.example.milsaborescompose.viewmodel.PaymentMethodUiState
import com.example.milsaborescompose.viewmodel.UserUiState
import androidx.compose.ui.test.performClick
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<androidx.activity.ComponentActivity>()

    @Test
    fun profileScreen_muestra_datos_del_usuario_logueado() {
        // 1. Arrange: Preparar el estado de la UI y los datos falsos
        val fakePaymentMethod = PaymentMethod(1L, "Tarjeta de Crédito")
        val fakeUser = User(
            id = 1L,
            name = "Fabián Lillo",
            mail = "fabian.lillo@example.com",
            password = "", // La contraseña nunca debe estar en el estado de la UI
            address = "Avenida Siempreviva 742",
            number = "+56912345678",
            paymentMethod = fakePaymentMethod
        )

        val fakeUiState = UserUiState(
            isLoggedIn = true,
            currentUser = fakeUser
        )

        val fakePaymentUiState = PaymentMethodUiState(
            paymentMethods = listOf(fakePaymentMethod)
        )

        // 2. Act: Renderizar el Composable con el estado simulado
        composeTestRule.setContent {
            MilSaboresComposeTheme {
                val mockNavController = mockk<NavController>(relaxed = true)
                ProfileScreen(
                    uiState = fakeUiState,
                    paymentUiState = fakePaymentUiState,
                    onLogout = {},
                    onLogoutSuccess = {},
                    onUpdateUser = { /* mock update */ },
                    onSaveProfilePicture = { /* mock save picture */ },
                    navController = mockNavController
                )
            }
        }

        // 3. Assert: Verificar que los datos del usuario se muestran en pantalla
        composeTestRule.onNodeWithText("Mi Perfil").assertIsDisplayed() // Cambié el texto según tu TopBar
        composeTestRule.onNodeWithText("Fabián Lillo").assertIsDisplayed()
        composeTestRule.onNodeWithText("fabian.lillo@example.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("Avenida Siempreviva 742").assertIsDisplayed()
        composeTestRule.onNodeWithText("+56912345678").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tarjeta de Crédito").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cerrar sesión").assertIsDisplayed() // Cambié por minúscula según tu código
    }
    @Test
    fun profileScreen_muestra_campos_edicion_cuando_en_modo_edicion() {
        // Arrange
        val fakePaymentMethod = PaymentMethod(1L, "Tarjeta de Crédito")
        val fakeUser = User(
            id = 1L,
            name = "Fabián Lillo",
            mail = "fabian.lillo@example.com",
            password = "",
            address = "Avenida Siempreviva 742",
            number = "+56912345678",
            paymentMethod = fakePaymentMethod
        )

        val fakeUiState = UserUiState(
            isLoggedIn = true,
            currentUser = fakeUser
        )

        val fakePaymentUiState = PaymentMethodUiState(
            paymentMethods = listOf(fakePaymentMethod)
        )

        // Act
        composeTestRule.setContent {
            MilSaboresComposeTheme {
                val mockNavController = mockk<NavController>(relaxed = true)
                ProfileScreen(
                    uiState = fakeUiState,
                    paymentUiState = fakePaymentUiState,
                    onLogout = {},
                    onLogoutSuccess = {},
                    onUpdateUser = { },
                    onSaveProfilePicture = { },
                    navController = mockNavController
                )
            }
        }

        // Click en "Editar Perfil" para entrar en modo edición
        composeTestRule.onNodeWithText("Editar Perfil").performClick()

        // Assert: Verificar que aparecen los campos de edición
        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Teléfono").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dirección").assertIsDisplayed()
        composeTestRule.onNodeWithText("Método de pago").assertIsDisplayed()
    }
}