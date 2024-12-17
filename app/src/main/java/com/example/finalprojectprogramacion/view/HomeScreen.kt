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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
            .background(Color.Black) // Fondo negro sin márgenes blancos
            .padding(horizontal = 0.dp), // Padding ajustado horizontalmente
        verticalArrangement = Arrangement.Top
    ) {
        // Título alineado a la derecha
        Text(
            text = "Mis Reservas",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp) // Padding superior y derecho
                .align(Alignment.Start),
            textAlign = TextAlign.Start // Alineación del texto
        )

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn para mostrar reservas
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Ocupa el espacio restante
                .padding(horizontal = 16.dp) // Márgenes internos ajustados
        ) {
            items(reservas.size) { index ->
                val reserva = reservas[index]

                // Tarjeta de reserva
                ReservaCard(
                    reserva = reserva,
                    nombreDiscotecas = discotecas,
                    onDelete = { reserva -> viewModel.deleteReserva(reserva) },
                    onUpdate = { reserva ->
                        navController.navigate("edit_reserva/${reserva.id}")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // FloatingActionButton ajustado
        FloatingActionButton(
            onClick = { navController.navigate("add_reserva") },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
            containerColor = Color(0xFF0056FF)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Añadir Reserva",
                tint = Color.White
            )
        }

        // Botones en fila
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate("discs") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0056FF),
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "Lista de Discotecas")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Lista")
            }

            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0056FF),
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Mis Reservas")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mis Reservas")
            }

            Button(
                onClick = { navController.navigate("map") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0056FF),
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Ver Mapa")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mapa")
            }
        }
    }
}
