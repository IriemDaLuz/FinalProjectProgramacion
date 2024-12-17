package com.example.finalprojectprogramacion.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalprojectprogramacion.viewmodel.ViewModelApp
import com.example.finalprojectprogramacion.data.Discoteca
@Composable
fun DiscScreen(viewModelApp: ViewModelApp = viewModel(), navController: NavController) {
    // Observa las discotecas y los tipos de discotecas en el ViewModel
    val discotecas = viewModelApp.discotecas.collectAsState().value
    val tiposDiscotecas = viewModelApp.tiposdiscotecas.collectAsState().value

    // Usamos una columna para organizar la pantalla, con el fondo negro
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Black) // Fondo negro
    ) {
        // Título de la pantalla en blanco
        Text(
            text = "Lista de Discotecas",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp) // Padding superior y derecho
                .align(Alignment.Start),
            textAlign = TextAlign.Start // Alineación del texto
        )

        // Mostrar las discotecas en un LazyColumn
        LazyColumn(modifier = Modifier
            .padding(horizontal = 16.dp)
            .weight(1f)) { // El peso asegura que ocupe el espacio restante
            items(discotecas.size) { index ->
                val discoteca = discotecas[index]
                val tipoDiscoteca = tiposDiscotecas[discoteca.IdTipoDiscoteca] ?: "Desconocido"
                DiscotecaCard(discoteca, tipoDiscoteca)
            }
        }

        // Espaciador para separar los botones del contenido
        Spacer(modifier = Modifier.height(16.dp))

        // Botones en fila sin padding adicional
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
                    containerColor = Color(0xFF0056FF), // Azul
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
                    containerColor = Color(0xFF0056FF), // Azul
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
                    containerColor = Color(0xFF0056FF), // Azul
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

@Composable
fun DiscotecaCard(discoteca: Discoteca, tipoDiscoteca: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Las tarjetas ocupan todo el ancho
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título de la discoteca con el tipo de discoteca al lado
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = discoteca.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF0056FF), // Azul para el nombre
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "- $tipoDiscoteca",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray // Gris oscuro para el tipo
                    )
                )
            }

            // Subtítulos en negrita para "Descripción", "Horario", "Capacidad", y "Teléfono"
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descripción:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Text(
                text = discoteca.description ?: "No description",
                color = Color.Black // Descripción en negro
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Horario:",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = discoteca.horario,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Capacidad:",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = discoteca.capacidad,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Teléfono:",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = discoteca.telefono ?: "No disponible",
                    color = Color.Black
                )
            }
        }
    }
}
