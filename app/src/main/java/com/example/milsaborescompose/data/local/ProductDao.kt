package com.example.milsaborescompose.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT DISTINCT category FROM products")
    fun getDistinctCategories(): Flow<List<String>>

    @Query("SELECT * FROM products WHERE category = :categoryName")
    fun getProductsByCategory(categoryName: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: Int): Flow<Product>

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
}
