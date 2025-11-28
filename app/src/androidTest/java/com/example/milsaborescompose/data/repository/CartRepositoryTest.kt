package com.example.milsaborescompose.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.milsaborescompose.data.model.CartItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartRepositoryTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var context: Context

    @Before
    fun setup() {
        // Obtenemos el contexto de la aplicación simulada
        context = ApplicationProvider.getApplicationContext()
        cartRepository = CartRepository(context)
        
        // Limpiamos el carrito antes de cada test para empezar de cero
        runBlocking {
            cartRepository.clearCart()
        }
    }

    @After
    fun tearDown() {
        // Limpiamos después de cada test
        runBlocking {
            cartRepository.clearCart()
        }
    }

    @Test
    fun agregarAlCarrito_debeAgregarNuevoItem() = runBlocking {
        // Arrange
        val item = CartItem(1, "Torta de Chocolate", 12000, "img_url", 1)

        // Act
        cartRepository.addToCart(item)
        
        // Assert
        val items = cartRepository.allCartItems.first()
        assertEquals(1, items.size)
        assertEquals("Torta de Chocolate", items[0].name)
    }

    @Test
    fun agregarAlCarrito_debeIncrementarCantidadSiItemExiste() = runBlocking {
        // Arrange
        val item1 = CartItem(1, "Torta de Chocolate", 12000, "img_url", 1)
        val item2 = CartItem(1, "Torta de Chocolate", 12000, "img_url", 2) // Mismo ID

        // Act
        cartRepository.addToCart(item1)
        cartRepository.addToCart(item2) // Debería sumar cantidad, no agregar nuevo item

        // Assert
        val items = cartRepository.allCartItems.first()
        assertEquals(1, items.size) // Sigue siendo 1 item en la lista
        assertEquals(3, items[0].quantity) // 1 + 2 = 3
    }

    @Test
    fun eliminarDelCarrito_debeRemoverItem() = runBlocking {
        // Arrange
        val item1 = CartItem(1, "Torta Uno", 1000, "url", 1)
        val item2 = CartItem(2, "Torta Dos", 2000, "url", 1)
        cartRepository.addToCart(item1)
        cartRepository.addToCart(item2)

        // Act
        cartRepository.deleteFromCart(item1)

        // Assert
        val items = cartRepository.allCartItems.first()
        assertEquals(1, items.size)
        assertEquals(2, items[0].productId) // Solo queda el item 2
    }

    @Test
    fun limpiarCarrito_debeRemoverTodosLosItems() = runBlocking {
        // Arrange
        val item = CartItem(1, "Torta", 1000, "url", 1)
        cartRepository.addToCart(item)

        // Act
        cartRepository.clearCart()

        // Assert
        val items = cartRepository.allCartItems.first()
        assertTrue(items.isEmpty())
    }
}