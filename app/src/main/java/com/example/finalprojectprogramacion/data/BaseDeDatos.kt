package com.example.finalprojectprogramacion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Discoteca::class, TipoDiscoteca::class, Reserva::class], version = 20)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun DiscotecaDao(): DiscotecaDao
    abstract fun TipoDiscotecaDao(): TipoDiscotecaDao
    abstract fun ReservaDao(): ReservaDao

    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun getDatabase(context: Context): BaseDeDatos {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "basededatos_discoteca"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
