package com.example.finalprojectprogramacion

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface ReservaDao {

    @Insert
    suspend fun insertReserva(reserva: Reserva): Long

    @Update
    suspend fun updateReserva(reserva: Reserva)

    @Delete
    suspend fun deleteReserva(reserva: Reserva)

    @Query("SELECT * FROM reservas")
    suspend fun getAllReservas(): List<Reserva>

    @Query("SELECT * FROM reservas WHERE id = :id")
    suspend fun getAllReservasById(id: Int): Reserva?

    @Query("SELECT * FROM reservas WHERE idDiscoteca = :idDiscoteca")
    suspend fun getAllReservasByDiscotecaId(idDiscoteca: Int): List<Reserva>
}
