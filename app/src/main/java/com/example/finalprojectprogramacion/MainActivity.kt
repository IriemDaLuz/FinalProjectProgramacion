package com.example.finalprojectprogramacion


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = BaseDeDatos.getDatabase(this)
        val DiscotecaDao = database.DiscotecaDao()
        val TipoDiscotecaDao = database.TipoDiscotecaDao()
        val ReservaDao = database.ReservaDao()

        val ViewModelDiscotecaApp = ViewModelApp(DiscotecaDao, TipoDiscotecaDao,ReservaDao)

        setContent {
            MainApp(ViewModelDiscotecaApp)
        }
    }
}