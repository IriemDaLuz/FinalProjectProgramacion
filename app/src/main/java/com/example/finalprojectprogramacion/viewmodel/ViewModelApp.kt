package com.example.finalprojectprogramacion

import android.util.Log
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

    // Estado que guarda las reservas. Usamos un MutableStateFlow para poder modificarlo.
    private val _reservas = MutableStateFlow<Map<Int, String>>(emptyMap())
    val reservas: StateFlow<Map<Int, String>> = _reservas

    // Inicialización: se cargan los datos de lugares y tipos de lugares cuando se crea el ViewModel
    init {
        cargarMarcadores() // Cargar lugares
        cargarTiposMarcadores() // Cargar tipos de lugares
        loadReservas()
    }

    // Función para cargar los lugares desde la base de datos
    private fun cargarMarcadores() {
        viewModelScope.launch(Dispatchers.IO) {
            val discotecaList = discotecaDao.getAllMarkers()
            _discotecas.value =discotecaList
        }
    }
    private fun loadReservas() {
        viewModelScope.launch {
            _reservas.value = ReservaDao.getAllReservas()
        }
    }

    // Función para cargar los tipos de lugares desde la base de datos
    private fun cargarTiposMarcadores() {
        viewModelScope.launch(Dispatchers.IO) {
            val types = tipoDiscotecaDao.getAllMarkerTypes()
            _tiposdiscotecas.value = types.associate { it.id to it.name }
        }
    }
    private fun insertarDiscotecas() {
        // Lista de discotecas con los detalles de cada una
        val discotecas = listOf(
            Discoteca(
                name ="Changó Lanzarote",
                latitude = "28.96192",
                longitude = "-13.55520",
                IdTipoDiscoteca = 2,
                horario = "22:00 - 04:00",
                capacidad = "50 personas",
                telefono = "+34 928 111 222"
            ),Discoteca(
                name = "Karma",
                latitude = "28.96226",
                longitude = "-13.53921",
                IdTipoDiscoteca = 3,
                horario = "22:00 - 06:00",
                capacidad = "90 personas"
            ),
            Discoteca(
                name = "Manhattan Cocktail Bar",
                latitude = "28.95809",
                longitude = "-13.55349",
                IdTipoDiscoteca = 3,
                description = "Lounge y discoteca con un ambiente cosmopolita.",
                horario = "11:00 - 03:00",
                capacidad = "100 personas",
                telefono = "+34 609010125"
            ),
            Discoteca(
                name = "Four Seasons Rock Cafè",
                latitude = "28.99682",
                longitude = "-13.49163",
                IdTipoDiscoteca = 1,
                description = "Un pub con música variada y excelente ambiente nocturno.",
                horario = "23:00 - 06:00",
                capacidad = "120 personas",
                telefono = "+34 828 91 01 07"
            ),
            Discoteca(
                name = "Rockola Matagorda",
                latitude = "28.94028",
                longitude = "-13.59956",
                IdTipoDiscoteca = 1,
                horario = "22:00 - 04:00",
                capacidad = "50 personas"
            ),
        )

        // Inserción en la base de datos usando una corrutina
        CoroutineScope(Dispatchers.IO).launch {
            discotecas.forEach { discoteca ->
                discotecaDao.insert(discoteca)
            }
        }
    }


    private fun insertarTiposDiscotecas() {
        val tiposDiscotecas = listOf(
            TipoDiscoteca(name = "Rock"),
            TipoDiscoteca(name = "Latina"),
            TipoDiscoteca(name = "Reggaeton"),
            TipoDiscoteca(name = "Variada")
        )

        CoroutineScope(Dispatchers.IO).launch {
            tiposDiscotecas.forEach { tipoDiscoteca ->
                tipoDiscotecaDao.insertMarkerType(tipoDiscoteca)
            }
        }
    }

    fun addReserva(name: String,fechaReserva, fechaEvento,idDiscoteca,cantidadPersonas,estado="confirmado") {
        if (name.isBlank() || idDiscoteca.isBlank()) return

        viewModelScope.launch {
            try {
                val discoteca = reservaDao.getAllReservasByDiscotecaId(idDiscoteca)
                if (discoteca != null) {
                    val newReserva = Reserva(
                        id = 0,
                        fechaReserva = fechaReserva,
                        fechaEvento = fechaEvento,
                        idDiscoteca=idDiscoteca,
                        cantidadPersonas=cantidadPersonas,
                        estado = "confirmado"
                    )
                    reservaDao.insertReserva(newReserva)
                    loadReservas() // Recargar reserva
                } else {
                    Log.e("REservaViewModel", "Discoteca no encontrado: $idDiscoteca")
                }
            } catch (e: Exception) {
                Log.e("REservaViewModel", "Error al añadir tarea: ${e.message}")
            }
        }
    }


    fun deleteReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                reservaDao.deleteReserva(reserva)
                loadReservas()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al eliminar tarea: ${e.message}")
            }
        }
    }

    fun updateReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                reservaDao.updateReserva(reserva)
                loadReservas()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar tarea: ${e.message}")
            }
        }
    }
}