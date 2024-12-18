package com.example.finalprojectprogramacion.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "reservas",
    foreignKeys = [
        ForeignKey(
            entity = Reserva::class,
            parentColumns = ["id"],
            childColumns = ["idDiscoteca"],
        )
    ]
)

data class Reserva(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String, // Agregado este campo
    var fechaReserva: String,
    var fechaEvento: String,
    val idDiscoteca: Int,
    var cantidadPersonas: Int,
    var estado: String

)