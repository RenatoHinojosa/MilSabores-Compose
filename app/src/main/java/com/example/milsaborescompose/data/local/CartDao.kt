package com.example.milsaborescompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Update
    suspend fun updateCart(cartItem: CartItem)

    @Delete
    suspend fun deleteFromCart(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun findItemById(productId: Int): CartItem?
}
