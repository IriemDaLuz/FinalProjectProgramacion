package com.example.finalprojectprogramacion.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "discotecas",
    foreignKeys = [
        ForeignKey(
            entity = TipoDiscoteca::class,
            parentColumns = ["id"],
            childColumns = ["IdTipoDiscoteca"],
        )
    ]
)

data class Discoteca(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val latitude: String,
    val longitude: String,
    val IdTipoDiscoteca: Int,
    val description: String?=null ,
    val horario: String,
    val capacidad: String,
    val telefono: String? =null
)