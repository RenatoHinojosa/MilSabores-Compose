package com.example.milsaborescompose.repository

import com.example.milsaborescompose.data.model.PaymentMethod
import com.example.milsaborescompose.data.model.User
import com.example.milsaborescompose.data.remote.UserService
import com.example.milsaborescompose.data.repository.UserRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class UserRepositoryTest : StringSpec({

    "getUserById debe retornar un usuario simulado" {
        // 1. Preparación (Arrange)
        val fakePaymentMethod = PaymentMethod(1L, "Tarjeta de Crédito")
        val fakeUser = User(
            id = 1L,
            name = "Fabián Lillo",
            mail = "fabian.lillo@example.com",
            password = "password",
            address = "123 Calle Falsa",
            number = "987654321",
            paymentMethod = fakePaymentMethod
        )
        val userId = 1L

        // Creamos el mock del servicio
        val mockUserService = mockk<UserService>()

        // Definimos el comportamiento del mock: cuando se llame a getUserById(1L), debe devolver nuestro fakeUser
        coEvery { mockUserService.getUserById(userId) } returns fakeUser

        // Creamos la instancia del repositorio con el servicio simulado
        val repo = UserRepository(mockUserService)

        // 2. Acción (Act)
        runTest {
            val result = repo.getUserById(userId)

            // 3. Aserción (Assert)
            result shouldBe fakeUser
        }
    }
})