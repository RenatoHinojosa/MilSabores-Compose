package com.example.milsaborescompose.data.repository

import com.example.milsaborescompose.data.model.Product
import com.example.milsaborescompose.data.remote.ProductService

class ProductRepository(private val productService: ProductService) {

    suspend fun getAllProducts(): List<Product> {
        return productService.getAllProducts()
    }

    suspend fun getProductById(id: Long): Product {
        return productService.getProductById(id)
    }

    suspend fun getProductsByCategory(categoryId: Long): List<Product> {
        return productService.getProductsByCategory(categoryId)
    }

    suspend fun createProduct(product: Product): Product {
        return productService.createProduct(product)
    }

    suspend fun updateProduct(id: Long, product: Product): Product {
        return productService.updateProduct(id, product)
    }

    suspend fun deleteProduct(id: Long) {
        productService.deleteProduct(id)
    }
}
