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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalprojectprogramacion.data.Reserva
import com.example.finalprojectprogramacion.viewmodel.ViewModelApp
import com.example.finalprojectprogrtamacion.view.ReservaCard

@Composable
fun HomeScreen(navController: NavController, viewModel: ViewModelApp) {
    // Obtener la lista de reservas desde el ViewModel
    val reservas = viewModel.reservas.collectAsState().value
    val discotecas = viewModel.discotecas.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Top // Alineamos los elementos al principio de la columna
    ) {
        // Título de Reservas de Discotecas al principio
        Text("Reservas de Discotecas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn para mostrar reservas
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(reservas.size) { index ->
                val reserva = reservas[index]

                // ReservaCard para cada reserva
                ReservaCard(
                    reserva = reserva,
                    nombreDiscotecas = discotecas,
                    onDelete = { reserva -> viewModel.deleteReserva(reserva) },
                    onUpdate = { reserva ->
                        // Navegar a la pantalla de edición
                        navController.navigate("edit_reserva/${reserva.id}")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Espacio para los botones
        Spacer(modifier = Modifier.weight(1f)) // Esto empuja los botones hacia abajo

        FloatingActionButton(
            onClick = { navController.navigate("add_reserva") }, // Navegar a la pantalla para añadir reserva
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp), // Ajusta el padding para dejar espacio
            containerColor = MaterialTheme.colorScheme.primary // Color del botón
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Añadir Reserva",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { navController.navigate("list") }) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Lista de Discotecas"
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Button(onClick = { navController.navigate("home") }) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Mis Reservas")
                Spacer(modifier = Modifier.width(8.dp))
            }

            Button(onClick = { navController.navigate("map") }) {
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Ver Mapa")
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
