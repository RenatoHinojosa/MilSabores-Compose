package com.example.milsaborescompose.data.repository

import com.example.milsaborescompose.data.local.Product
import com.example.milsaborescompose.data.local.ProductDao
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {
    val products: Flow<List<Product>> = dao.getAllProducts()
    val categories: Flow<List<String>> = dao.getDistinctCategories()

    fun getProductsByCategory(category: String): Flow<List<Product>> = dao.getProductsByCategory(category)

    fun getProductById(id: Int): Flow<Product> = dao.getProductById(id)

    suspend fun insert(product: Product) = dao.insertProduct(product)
    suspend fun update(product: Product) = dao.updateProduct(product)
    suspend fun delete(product: Product) = dao.deleteProduct(product)
}
