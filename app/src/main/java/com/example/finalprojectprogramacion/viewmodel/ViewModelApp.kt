package com.example.finalprojectprogramacion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectprogramacion.data.Discoteca
import com.example.finalprojectprogramacion.data.DiscotecaDao
import com.example.finalprojectprogramacion.data.Reserva
import com.example.finalprojectprogramacion.data.ReservaDao
import com.example.finalprojectprogramacion.data.TipoDiscoteca
import com.example.finalprojectprogramacion.data.TipoDiscotecaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelApp(
    private val discotecaDao: DiscotecaDao,
    private val tipoDiscotecaDao: TipoDiscotecaDao,
    private val reservaDao: ReservaDao
) : ViewModel() {

    // Estados observables
    private val _discotecas = MutableStateFlow<List<Discoteca>>(emptyList())
    val discotecas: StateFlow<List<Discoteca>> = _discotecas

    private val _tiposdiscotecas = MutableStateFlow<Map<Int, String>>(emptyMap())
    val tiposdiscotecas: StateFlow<Map<Int, String>> = _tiposdiscotecas

    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())
    val reservas: StateFlow<List<Reserva>> = _reservas

    init {
        //insertarTiposDiscotecas()
        //insertarDiscotecas()
        cargarDiscotecas()
        cargarTiposDiscotecas()
        cargarReservas()
    }

    private fun cargarDiscotecas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _discotecas.value = discotecaDao.getAllMarkers()
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al cargar discotecas: ${e.message}")
            }
        }
    }

    private fun cargarTiposDiscotecas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tipos = tipoDiscotecaDao.getAllMarkerTypes()
                _tiposdiscotecas.value = tipos.associate { it.id to it.name }
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al cargar tipos de discotecas: ${e.message}")
            }
        }
    }

    private fun cargarReservas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _reservas.value = reservaDao.getAllReservas()
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al cargar reservas: ${e.message}")
            }
        }
    }

    fun addReserva(
        name: String,
        fechaReserva: String,
        fechaEvento: String,
        idDiscoteca: Int,
        cantidadPersonas: Int,
        estado: String
    ) {
        if (name.isBlank() || idDiscoteca <= 0 || fechaReserva.isBlank() || fechaEvento.isBlank()) {
            Log.e("ViewModelApp", "Parámetros inválidos para agregar reserva")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val nuevaReserva = Reserva(
                    id = 0, // El ID será generado automáticamente por la base de datos
                    name = name,
                    fechaReserva = fechaReserva,
                    fechaEvento = fechaEvento,
                    idDiscoteca = idDiscoteca,
                    cantidadPersonas = cantidadPersonas,
                    estado = estado
                )
                reservaDao.insertReserva(nuevaReserva)
                cargarReservas() // Recargar reservas
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al agregar reserva: ${e.message}")
            }
        }
    }

    fun deleteReserva(reserva: Reserva) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                reservaDao.deleteReserva(reserva)
                cargarReservas() // Recargar reservas
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al eliminar reserva: ${e.message}")
            }
        }
    }

    fun updateReserva(
        reservaId: Int,
        fechaReserva: String,
        fechaEvento: String,
        cantidadPersonas: Int,
        estado: String
    ) {
        if (reservaId <= 0 || fechaReserva.isBlank() || fechaEvento.isBlank()) {
            Log.e("ViewModelApp", "Parámetros inválidos para actualizar reserva")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val reserva = reservaDao.getAllReservasById(reservaId)
                if (reserva != null) {
                    reserva.fechaReserva = fechaReserva
                    reserva.fechaEvento = fechaEvento
                    reserva.cantidadPersonas = cantidadPersonas
                    reserva.estado = estado
                    reservaDao.updateReserva(reserva)
                    cargarReservas() // Recargar reservas
                } else {
                    Log.e("ViewModelApp", "Reserva no encontrada: $reservaId")
                }
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al actualizar reserva: ${e.message}")
            }
        }
    }

    fun insertarDiscotecas() {
        val discotecas = listOf(
            Discoteca(
                name = "Changó Lanzarote",
                latitude = "28.96192",
                longitude = "-13.55520",
                IdTipoDiscoteca = 2,
                horario = "22:00 - 04:00",
                capacidad = "50 personas",
                telefono = "+34 928 111 222"
            ),
            Discoteca(
                name = "Karma",
                latitude = "28.96226",
                longitude = "-13.53921",
                IdTipoDiscoteca = 3,
                horario = "22:00 - 06:00",
                capacidad = "90 personas"
            )
            // Agrega más discotecas según sea necesario
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                discotecas.forEach { discoteca ->
                    discotecaDao.insert(discoteca)
                }
                cargarDiscotecas()
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al insertar discotecas: ${e.message}")
            }
        }
    }

    fun insertarTiposDiscotecas() {
        val tiposDiscotecas = listOf(
            TipoDiscoteca(name = "Rock"),
            TipoDiscoteca(name = "Latina"),
            TipoDiscoteca(name = "Reggaeton"),
            TipoDiscoteca(name = "Variada")
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                tiposDiscotecas.forEach { tipo ->
                    tipoDiscotecaDao.insertMarkerType(tipo)
                }
                cargarTiposDiscotecas()
            } catch (e: Exception) {
                Log.e("ViewModelApp", "Error al insertar tipos de discotecas: ${e.message}")
            }
        }
    }
}
