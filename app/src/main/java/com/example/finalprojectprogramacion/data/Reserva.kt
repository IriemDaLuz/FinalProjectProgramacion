package com.example.finalprojectprogramacion

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
    val id: Int = 0,
    val fechaReserva: String,
    val fechaEvento: String,
    val idDiscoteca: Int,
    val cantidadPersonas: Int? = null,
    val estado: String

)