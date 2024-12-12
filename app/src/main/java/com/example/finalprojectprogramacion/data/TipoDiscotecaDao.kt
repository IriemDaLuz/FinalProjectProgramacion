package com.example.finalprojectprogramacion

import androidx.room.*

@Dao interface TipoDiscotecaDao {
    @Insert
    suspend fun insertMarkerType(markerType: TipoDiscotecaDao)

    @Query("SELECT * FROM tiposdiscotecas")
    suspend fun getAllMarkerTypes(): List<TipoDiscotecaDao>

    @Query("SELECT * FROM tiposdiscotecas WHERE id = :id")
    fun getMarkTypeById(id: Int): TipoDiscotecaDao?
}