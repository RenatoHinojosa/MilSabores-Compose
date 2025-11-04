package com.example.milsaborescompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val telefono: String,
    val metodoDePago: MetodoDePago,
    val profilePictureUri: String? = null
)
