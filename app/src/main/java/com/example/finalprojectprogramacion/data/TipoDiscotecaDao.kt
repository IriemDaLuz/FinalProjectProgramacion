package com.example.finalprojectprogramacion.data

import androidx.room.*

@Dao interface TipoDiscotecaDao {
    @Insert
    suspend fun insertMarkerType(markerType: TipoDiscoteca)

    @Query("SELECT * FROM tiposdiscotecas")
    suspend fun getAllMarkerTypes(): List<TipoDiscoteca>

    @Query("SELECT * FROM tiposdiscotecas WHERE id = :id")
    fun getMarkTypeById(id: Int): TipoDiscoteca?
}