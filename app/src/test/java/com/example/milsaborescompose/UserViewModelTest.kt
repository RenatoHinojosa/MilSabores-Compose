package com.example.milsaborescompose

import com.example.milsaborescompose.data.model.PaymentMethod
import com.example.milsaborescompose.data.model.User
import com.example.milsaborescompose.data.model.auth.LoginRequest
import com.example.milsaborescompose.data.model.auth.LoginResponse
import com.example.milsaborescompose.data.repository.SessionRepository
import com.example.milsaborescompose.data.repository.UserRepository
import com.example.milsaborescompose.viewmodel.UserViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest : StringSpec({

    // Dispatcher de prueba para controlar las corrutinas
    val testDispatcher = StandardTestDispatcher()

    // Se ejecuta antes de cada test para reemplazar el Dispatcher principal
    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    // Se ejecuta después de cada test para limpiar el dispatcher
    afterTest {
        Dispatchers.resetMain()
    }

    "login exitoso debe actualizar el uiState a isLoggedIn = true" {
        // 1. Arrange: Preparar datos y mocks
        val fakeLoginRequest = LoginRequest("test@test.com", "password")
        val fakePaymentMethod = PaymentMethod(1L, "Efectivo")
        val fakeLoginResponse = LoginResponse(
            id = 1L,
            token = "fake-jwt-token",
            email = "test@test.com",
            name = "Test User",
            number = "123456",
            address = "Calle Falsa 123",
            paymentMethod = fakePaymentMethod
        )
        val fakeUser = User(
            id = 1L,
            name = "Test User",
            mail = "test@test.com",
            password = "", // La contraseña no se guarda en el estado del usuario
            address = "Calle Falsa 123",
            number = "123456",
            paymentMethod = fakePaymentMethod
        )

        val mockUserRepository = mockk<UserRepository>()
        val mockSessionRepository = mockk<SessionRepository>()

        // Definir comportamiento de los mocks
        coEvery { mockUserRepository.login(fakeLoginRequest) } returns fakeLoginResponse
        coEvery { mockUserRepository.getUserById(1L) } returns fakeUser
        coEvery { mockSessionRepository.saveSession(1L, "fake-jwt-token") } just runs
        // Simulamos que no hay sesión activa al iniciar el ViewModel
        coEvery { mockSessionRepository.getSession() } returns flowOf(null)

        // 2. Act: Crear el ViewModel y llamar a la función
        val viewModel = UserViewModel(mockUserRepository, mockSessionRepository)
        viewModel.login(fakeLoginRequest)

        // Avanzar el dispatcher para que se ejecuten las corrutinas lanzadas en viewModelScope
        testDispatcher.scheduler.advanceUntilIdle()

        // 3. Assert: Comprobar el estado final
        val finalState = viewModel.uiState.value
        finalState.isLoggedIn shouldBe true
        finalState.currentUser shouldBe fakeUser
        finalState.loginError shouldBe null
        finalState.isLoading shouldBe false
    }
})