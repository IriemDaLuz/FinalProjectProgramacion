package com.example.finalprojectprogrtamacion

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import com.example.finalprojectprogramacion.Discoteca
import com.example.finalprojectprogramacion.Reserva

@Composable
fun ReservaCard(reserva: Reserva,
                nombreDiscotecas: List<Discoteca>,
                onDelete: (Reserva) -> Unit,
                onUpdate: (Reserva) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var cantvisibilidad by remember { mutableStateOf("") }

    val nameDiscoteca = nombreDiscotecas.find { it.id == reserva.idDiscoteca }?.name ?: "Sin tipo"

        Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val animatedPadding by animateDpAsState(
            targetValue = if (expanded) 48.dp else 18.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Row(
            modifier=Modifier.padding(vertical = animatedPadding, horizontal = 8.dp),)
        {
            Text(reserva.id.toString())
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(reserva.fechaEvento)
                    Text(nameDiscoteca)
                }
                Row {
                    Button(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        if (expanded) {
                            cantvisibilidad = reserva.cantidadPersonas.toString()
                        }
                        Text(if (expanded) "Cerrar Descripcion" else "Mostrar descripcion")
                        Column {
                            Text(cantvisibilidad)
                            Row {
                                Text(reserva.estado)
                                Text("Dia hecha la reserva" + reserva.estado)
                            }
                        }
                    }
                }
            }
            Column {
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { onUpdate(reserva) },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = Color.White
                    )
                }
                Button(
                    onClick = { onDelete(reserva) },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}



