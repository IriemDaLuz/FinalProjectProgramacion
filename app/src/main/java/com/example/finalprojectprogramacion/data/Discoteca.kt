package com.example.finalprojectprogramacion

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

data class Discoteca(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val latitude: String,
    val longitude: String,
    val IdTipoDiscoteca: Int,
    val description: String,
    val horario: String,
    val capacidad: String,
    val telefono: String
)