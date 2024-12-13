package com.example.finalprojectprogramacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelApp(
    private val discotecaDao: DiscotecaDao,
    private val tipoDiscotecaDao: TipoDiscotecaDao,
    val reservaDao: ReservaDao
) : ViewModel() {

    // Estado que guarda la lista de lugares. Usamos un MutableStateFlow para poder modificarlo.
    private val _discotecas = MutableStateFlow<List<Discoteca>>(emptyList())
    val discotecas: StateFlow<List<Discoteca>> = _discotecas

    // Estado que guarda los tipos de lugares. Usamos un MutableStateFlow para poder modificarlo.
    private val _tiposdiscotecas = MutableStateFlow<Map<Int, String>>(emptyMap())
    val tiposdiscotecas: StateFlow<Map<Int, String>> = _tiposdiscotecas


    // Función para cargar los lugares desde la base de datos
    private fun cargarMarcadores() {
        viewModelScope.launch(Dispatchers.IO) {
            val discotecaList = discotecaDao.getAllMarkers()
            _discotecas.value =discotecaList
        }
    }

    // Función para cargar los tipos de lugares desde la base de datos
    private fun cargarTiposMarcadores() {
        viewModelScope.launch(Dispatchers.IO) {
            val types = tipoDiscotecaDao.getAllMarkerTypes()
            _tiposdiscotecas.value = types.associate { it.id to it.name }
        }
    }


}