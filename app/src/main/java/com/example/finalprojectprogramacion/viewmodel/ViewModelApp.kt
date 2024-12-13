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
   private val _discotecas = MutableStateFlow<List<Discoteca>>(emptyList())
    val discotecas: StateFlow<List<Discoteca>> = _discotecas
   private val _tiposdiscotecas = MutableStateFlow<Map<Int, String>>(emptyMap())
    val tiposdiscotecas: StateFlow<Map<Int, String>> = _tiposdiscotecas
}