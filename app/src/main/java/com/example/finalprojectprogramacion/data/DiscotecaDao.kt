package com.example.finalprojectprogramacion.data

import androidx.room.*

@Dao interface DiscotecaDao {
    @Insert
    suspend fun insert(marker: Discoteca)

    @Delete
    suspend fun delete(marker: Discoteca?)

    @Query("SELECT * FROM discotecas")
    suspend fun getAllMarkers(): List<Discoteca>

    @Query("SELECT * FROM discotecas WHERE id = :id")
    fun getMarkerById(id: Int): Discoteca?
}