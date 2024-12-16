package com.example.finalprojectprogramacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = BaseDeDatos.getDatabase(this)
        val discotecaDao = database.DiscotecaDao()
        val tipoDiscotecaDao = database.TipoDiscotecaDao()
        val reservaDao = database.ReservaDao()

        val ViewModelApp = ViewModelApp(discotecaDao, tipoDiscotecaDao, reservaDao)

        setContent {
            MainApp(
                viewModelApp= ViewModelApp
            )
        }
    }
}
