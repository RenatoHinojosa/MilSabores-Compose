package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.model.CategoriaItem
import com.example.milsaborescompose.data.model.ProductType
import com.example.milsaborescompose.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CatalogUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoriaItem> = emptyList(),
    val error: String? = null
)

class CatalogViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val products = repository.getAllProducts()
                val uniqueProductTypes = products.map { it.productType }.distinctBy { it.id }
                val categoriaItems = uniqueProductTypes.map { productType ->
                    mapProductTypeToCategoriaItem(productType)
                }
                _uiState.update {
                    it.copy(isLoading = false, categories = categoriaItems)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Failed to load categories: ${e.message}")
                }
            }
        }
    }

    private fun mapProductTypeToCategoriaItem(productType: ProductType): CategoriaItem {
        return when (productType.id) {
            1L -> CategoriaItem(
                nombre = "Tortas Circulares",
                categoriaId = 1L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/003.jpg"
            )
            2L -> CategoriaItem(
                nombre = "Tortas Cuadradas",
                categoriaId = 2L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/221.jpg"
            )
            3L -> CategoriaItem(
                nombre = "Postres Individuales",
                categoriaId = 3L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/108.jpg"
            )
            4L -> CategoriaItem(
                nombre = "Productos Sin Azúcar",
                categoriaId = 4L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/060.jpg"
            )
            5L -> CategoriaItem(
                nombre = "Pastelería Tradicional",
                categoriaId = 5L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/087.jpg"
            )
            6L -> CategoriaItem(
                nombre = "Productos sin Gluten",
                categoriaId = 6L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/226.jpg"
            )
            7L -> CategoriaItem(
                nombre = "Productos Veganos",
                categoriaId = 7L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/227.jpg"
            )
            8L -> CategoriaItem(
                nombre = "Tortas Especiales",
                categoriaId = 8L,
                imagen = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/014.jpg"
            )
            else -> CategoriaItem(
                nombre = productType.name.replaceFirstChar { it.uppercase() },
                categoriaId = productType.id,
                imagen = "" // Default image
            )
        }
    }
}