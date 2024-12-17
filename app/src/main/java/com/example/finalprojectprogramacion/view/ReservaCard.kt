package com.example.finalprojectprogrtamacion.view

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
import androidx.compose.material3.IconButton
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

    // La card que contendrá la información
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Animación para el padding
        val animatedPadding by animateDpAsState(
            targetValue = if (expanded) 48.dp else 18.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        // Contenedor de la Card
        Row(
            modifier = Modifier
                .padding(vertical = animatedPadding, horizontal = 8.dp)
        ) {
            // Muestra el id de la reserva
            Text(reserva.id.toString())

            // Columna principal con la información
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Información de la reserva (fecha, discoteca)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(nameDiscoteca)
                    Text(reserva.fechaEvento)
                }

                // Si la tarjeta está expandida, mostramos la cantidad de personas y estado
                if (expanded) {
                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        Text("Cantidad de personas: ${reserva.cantidadPersonas}")
                        Text("Estado: ${reserva.estado}")
                        Text("Fecha de reserva: ${reserva.fechaReserva}")
                    }
                }

        }
    }
}
