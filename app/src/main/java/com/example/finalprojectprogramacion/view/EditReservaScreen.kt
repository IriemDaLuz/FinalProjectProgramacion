package com.example.finalprojectprogramacion.view

import android.util.Log
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReservaScreen(
    discotecaId: Int,
    viewModelApp: ViewModelApp,
    navController: NavController,
) {
    val discotecas by viewModelApp.discotecas.collectAsState()

    // Estado de la discoteca seleccionada
    val discotecaSeleccionada = remember {
        mutableStateOf(discotecas.find { it.id == discotecaId } ?: discotecas.firstOrNull())
    }

    // Generar la fecha actual al iniciar la pantalla
    val fechaCreacionReserva = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis())
    }

    // Campos inicializados
    var fechaEvento by remember { mutableStateOf("") }
    var cantidadPersonas by remember { mutableStateOf(1) }
    var nombreReserva by remember { mutableStateOf(discotecaSeleccionada.value?.name ?: "") }

    // Estado del menú desplegable
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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

        // Campo de texto para ingresar el nombre de la reserva
        OutlinedTextField(
            value = nombreReserva,
            onValueChange = { nombreReserva = it },
            label = { Text("Nombre de la Reserva", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de discoteca con DropdownMenu
        Text(
            text = "Selecciona una discoteca",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        androidx.compose.material3.ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            androidx.compose.material3.TextField(
                value = discotecaSeleccionada.value?.name ?: "Selecciona una discoteca",
                onValueChange = {},
                readOnly = true,
                label = { Text("Discoteca", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Abrir Menú",
                            tint = Color.White
                        )
                    }
                },
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.Black,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                discotecas.forEach { discoteca ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                discoteca.name,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            discotecaSeleccionada.value = discoteca
                            expanded = false
                        },
                        modifier = Modifier.background(Color.Black) // Fondo negro
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de fecha del evento
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
                Log.d("EditReservaScreen", "Discoteca seleccionada: ${discotecaSeleccionada.value?.id}")
                viewModelApp.addReserva(
                    name = nombreReserva.ifEmpty { "Nueva Reserva" },
                    fechaReserva = fechaCreacionReserva, // Fecha de creación automática
                    fechaEvento = fechaEvento,
                    idDiscoteca = discotecaSeleccionada.value?.id ?: -1,
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
