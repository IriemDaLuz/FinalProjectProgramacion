package com.example.finalprojectprogramacion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Discoteca::class, TipoDiscoteca::class,Reserva::class], version = 9)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun DiscotecaDao(): DiscotecaDao
    abstract fun TipoDiscotecaDao(): TipoDiscotecaDao
    abstract fun ReservaDao(): REservaDao


}