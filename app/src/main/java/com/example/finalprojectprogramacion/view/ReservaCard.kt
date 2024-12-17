package com.example.finalprojectprogrtamacion.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.finalprojectprogramacion.data.Discoteca
import com.example.finalprojectprogramacion.data.Reserva

@Composable
fun ReservaCard(
    reserva: Reserva,
    nombreDiscotecas: List<Discoteca>,
    onDelete: (Reserva) -> Unit,
    onUpdate: (Reserva) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Encuentra el nombre de la discoteca
    val nameDiscoteca = nombreDiscotecas.find { it.id == reserva.idDiscoteca }?.name ?: "Sin tipo"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Primera fila: nombre de la discoteca y fecha del evento
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nameDiscoteca,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = reserva.fechaEvento,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Descripción expandible
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    Text("Cantidad de personas: ${reserva.cantidadPersonas}")
                    Text("Estado: ${reserva.estado}")
                    Text("Fecha de reserva: ${reserva.fechaReserva}")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Botón para expandir/cerrar la descripción
                Button(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(alignment = Alignment.CenterVertically),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0056FF), // Color de fondo del botón
                        contentColor = Color.White
                    )
                ){
                    Text(if (expanded) "Cerrar Descripción" else "Mostrar Descripción")
                }
                // Fila con botones de editar y eliminar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    IconButton(
                        onClick = { onUpdate(reserva) },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = Color(0xFF0056FF)
                        )
                    }
                    IconButton(
                        onClick = { onDelete(reserva) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFF0056FF)
                        )
                    }
                }
            }
        }
    }

}


