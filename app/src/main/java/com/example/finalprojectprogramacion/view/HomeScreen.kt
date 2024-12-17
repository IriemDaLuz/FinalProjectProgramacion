package com.example.finalprojectprogramacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalprojectprogrtamacion.ReservaCard


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Reservas de Discotecas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        //Espacio para Tarjetas de Reservas

        // Mostrar tareas existentes
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reservas) { task ->
                ReservaCard { }(
                    task = task,
                    discotecas=discotecas// Pasar la lista de tipos de tarea aquí
                    onDelete = { viewModelApp.delete(reserva) },
                    onUpdate = { updatedTask ->
                        showAddTaskScreen=true
                        modoEdicion = updatedTask
                        newTaskName = updatedTask.name
                        selectedTaskTypeName = taskTypes.find { it.id.toLong() == updatedTask.id_tipostareas }?.title ?: ""
                        newTaskDescrip = updatedTask.description
                    }
                )
            }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { navController.navigate("list") }) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "Lista de Discotecas")
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

