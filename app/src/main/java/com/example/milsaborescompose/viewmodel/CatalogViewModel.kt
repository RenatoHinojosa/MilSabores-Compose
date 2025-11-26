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
                imagen = "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg"
            )
            2L -> CategoriaItem(
                nombre = "Tortas Cuadradas",
                categoriaId = 2L,
                imagen = "https://cocinerosargentinos.com/content/recipes/original/recipes.20871.jpg"
            )
            3L -> CategoriaItem(
                nombre = "Postres Individuales",
                categoriaId = 3L,
                imagen = "https://cocinemosjuntos.com.co/media/catalog/product/cache/5773068ed9b4f222a4301212c252d702/f/l/flan-de-caramelo-con-frutos-rojos_1__1.jpg"
            )
            4L -> CategoriaItem(
                nombre = "Productos Sin Azúcar",
                categoriaId = 4L,
                imagen = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg"
            )
            5L -> CategoriaItem(
                nombre = "Pastelería Tradicional",
                categoriaId = 5L,
                imagen = "https://www.tipicochileno.cl/wp-content/uploads/2025/06/News_Gato_NuevoDiseno-junio_c-SOPAIPILLAS.png"
            )
            6L -> CategoriaItem(
                nombre = "Productos sin Gluten",
                categoriaId = 6L,
                imagen = "https://chyfoodservice.cl/wp-content/uploads/2020/03/muffins-arandanos-singluten.jpg"
            )
            7L -> CategoriaItem(
                nombre = "Productos Veganos",
                categoriaId = 7L,
                imagen = "https://i.pinimg.com/736x/c0/7e/4e/c07e4e49847785119f1f0f91ac116c63.jpg"
            )
            8L -> CategoriaItem(
                nombre = "Tortas Especiales",
                categoriaId = 8L,
                imagen = "https://www.sorprendelima.pe/cdn/shop/files/IMG_4787.jpg?v=1709231148"
            )
            else -> CategoriaItem(
                nombre = productType.name.replaceFirstChar { it.uppercase() },
                categoriaId = productType.id,
                imagen = "" // Default image
            )
        }
    }
}