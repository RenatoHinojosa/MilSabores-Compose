package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.model.CategoriaItem
import com.example.milsaborescompose.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CatalogViewModel(repository: ProductRepository) : ViewModel() {

    val categorias: StateFlow<List<CategoriaItem>> = repository.categories
        .map { categoriasDb ->
            categoriasDb.map { categoria ->
                when (categoria) {
                    "circular" -> CategoriaItem(
                        nombre = "Tortas Circulares",
                        categoria = "circular",
                        imagen = "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg"
                    )
                    "cuadrada" -> CategoriaItem(
                        nombre = "Tortas Cuadradas",
                        categoria = "cuadrada",
                        imagen = "https://cocinerosargentinos.com/content/recipes/original/recipes.20871.jpg"
                    )
                    "individual" -> CategoriaItem(
                        nombre = "Postres Individuales",
                        categoria = "individual",
                        imagen = "https://cocinemosjuntos.com.co/media/catalog/product/cache/5773068ed9b4f222a4301212c252d702/f/l/flan-de-caramelo-con-frutos-rojos_1__1.jpg"
                    )
                    "sinazucar" -> CategoriaItem(
                        nombre = "Productos Sin Azúcar",
                        categoria = "sinazucar",
                        imagen = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg"
                    )
                    "tradicional" -> CategoriaItem(
                        nombre = "Pastelería Tradicional",
                        categoria = "tradicional",
                        imagen = "https://www.tipicochileno.cl/wp-content/uploads/2025/06/News_Gato_NuevoDiseno-junio_c-SOPAIPILLAS.png"
                    )
                    "singluten" -> CategoriaItem(
                        nombre = "Productos sin Gluten",
                        categoria = "singluten",
                        imagen = "https://chyfoodservice.cl/wp-content/uploads/2020/03/muffins-arandanos-singluten.jpg"
                    )
                    "vegano" -> CategoriaItem(
                        nombre = "Productos Veganos",
                        categoria = "vegano",
                        imagen = "https://i.pinimg.com/736x/c0/7e/4e/c07e4e49847785119f1f0f91ac116c63.jpg"
                    )
                    "especial" -> CategoriaItem(
                        nombre = "Tortas Especiales",
                        categoria = "especial",
                        imagen = "https://www.sorprendelima.pe/cdn/shop/files/IMG_4787.jpg?v=1709231148"
                    )
                    else -> CategoriaItem(
                        nombre = categoria.replaceFirstChar { it.uppercase() },
                        categoria = categoria,
                        imagen = ""
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
