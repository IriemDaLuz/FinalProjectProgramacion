package com.example.finalprojectprogramacion.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectprogramacion.data.Reserva
import com.example.finalprojectprogramacion.viewmodel.ViewModelApp
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.reflect.KProperty

@Composable
fun EditReservaScreen(
    discotecaId: Int,
    viewModelApp: ViewModelApp,
    navController: NavController,
) {
    val discotecas by viewModelApp.discotecas.collectAsState()

    // Encontrar la discoteca que corresponde al discotecaId
    val discoteca = discotecas.find { it.id == discotecaId }

    // Asegúrate de que la discoteca exista
    if (discoteca == null) {
        return // Maneja el caso de no encontrar la discoteca
    }

    var fechaReserva by remember { mutableStateOf("") }
    var fechaEvento by remember { mutableStateOf("") }
    var cantidadPersonas by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Editar Reserva", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        // Campos para editar la reserva
        OutlinedTextField(
            value = fechaReserva,
            onValueChange = { fechaReserva = it },
            label = { Text("Fecha de Reserva") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fechaEvento,
            onValueChange = { fechaEvento = it },
            label = { Text("Fecha del Evento") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidadPersonas.toString(),
            onValueChange = { cantidadPersonas = it.toIntOrNull() ?: 1 },
            label = { Text("Cantidad de Personas") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Aquí llamamos a la función para agregar/actualizar la reserva
                viewModelApp.addReserva(
                    name = "Reserva ${discoteca.name}",
                    fechaReserva = fechaReserva,
                    fechaEvento = fechaEvento,
                    idDiscoteca = discoteca.id,
                    cantidadPersonas = cantidadPersonas,
                    estado = "confirmado"
                )

                // Luego de añadir la reserva, navegar hacia la pantalla principal
                navController.popBackStack() // Regresar a la pantalla anterior (o hacia donde desees)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0056FF), // Azul
                contentColor = Color.White
            )
        ) {
            Text("Confirmar Reserva")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver a la pantalla anterior
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver"
            )
        }
    }
}
