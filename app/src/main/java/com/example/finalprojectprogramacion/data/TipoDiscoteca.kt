package com.example.finalprojectprogramacion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tiposdiscotecas")
data class TipoDiscoteca(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)