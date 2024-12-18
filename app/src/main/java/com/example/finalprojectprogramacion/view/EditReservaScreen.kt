package com.example.finalprojectprogramacion.view

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
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

    // Encontrar la discoteca correspondiente si el ID no es -1
    val discoteca = if (discotecaId != -1) {
        discotecas.find { it.id == discotecaId }
    } else null

    // Campos inicializados, vacíos si es una nueva reserva
    var fechaReserva by remember { mutableStateOf("") }
    var fechaEvento by remember { mutableStateOf("") }
    var cantidadPersonas by remember { mutableStateOf(1) }
    var nombreReserva by remember { mutableStateOf(discoteca?.name ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Título
        Text(
            text = if (discotecaId == -1) "Crear Nueva Reserva" else "Editar Reserva",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de nombre de reserva
        OutlinedTextField(
            value = nombreReserva,
            onValueChange = { nombreReserva = it },
            label = { Text("Nombre de la Reserva", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        // Campo de fecha de reserva
        OutlinedTextField(
            value = fechaReserva,
            onValueChange = { fechaReserva = it },
            label = { Text("Fecha de Reserva", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        // Campo de fecha de evento
        OutlinedTextField(
            value = fechaEvento,
            onValueChange = { fechaEvento = it },
            label = { Text("Fecha del Evento", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        // Campo de cantidad de personas
        OutlinedTextField(
            value = cantidadPersonas.toString(),
            onValueChange = { cantidadPersonas = it.toIntOrNull() ?: 1 },
            label = { Text("Cantidad de Personas", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para confirmar reserva
        Button(
            onClick = {
                viewModelApp.addReserva(
                    name = nombreReserva.ifEmpty { "Nueva Reserva" },
                    fechaReserva = fechaReserva,
                    fechaEvento = fechaEvento,
                    idDiscoteca = discoteca?.id ?: -1,
                    cantidadPersonas = cantidadPersonas,
                    estado = "pendiente"
                )
                navController.popBackStack() // Volver a la pantalla anterior
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0056FF),
                contentColor = Color.White
            )
        ) {
            Text("Confirmar Reserva")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }
    }
}
