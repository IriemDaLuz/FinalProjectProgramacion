package com.example.finalprojectprogramacion.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.finalprojectprogramacion.R
import com.example.finalprojectprogramacion.viewmodel.ViewModelApp
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreen(viewModelApp: ViewModelApp, navController: NavController) {
    // Usa collectAsState para obtener el estado y no uses rememberSaveable
    val discotecas by viewModelApp.discotecas.collectAsState()
    val tiposdiscotecas by viewModelApp.tiposdiscotecas.collectAsState()

    // Aquí ya no usamos rememberSaveable porque esas listas y mapas son datos complejos
    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(28.964967918770267, -13.553658852564096)
        zoom = 13.5
    }

    val mapProperties = DefaultMapProperties

    // Usamos OpenStreetMap para mostrar los marcadores
    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties.copy(
            tileSources = GoogleSat,
            isEnableRotationGesture = true
        )
    ) {
        discotecas.forEach { discoteca ->
            val discotecaState = rememberMarkerState(
                geoPoint = GeoPoint(discoteca.latitude.toDouble(), discoteca.longitude.toDouble())
            )

            val nombreTipoDiscoteca = tiposdiscotecas[discoteca.IdTipoDiscoteca]

            val context = LocalContext.current

            val imagenLugares = when (discoteca.IdTipoDiscoteca) {
                1 -> R.drawable.rock
                2 -> R.drawable.reggaeton
                3 -> R.drawable.salsa
                else -> R.drawable.variado
            }

            val drawable = ContextCompat.getDrawable(context, imagenLugares)

            // Marcador de la discoteca en el mapa
            Marker(
                state = discotecaState,
                title = discoteca.name,
                snippet = nombreTipoDiscoteca,
                icon = drawable
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color(0x9A3044B2), shape = RoundedCornerShape(40.dp))
                        .border(1.dp, Color.White, shape = RoundedCornerShape(40.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = it.snippet,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (discoteca.description != null) {
                        Text(
                            text = discoteca.description,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    // Botón para volver a la pantalla anterior
                    Spacer(modifier = Modifier.height(16.dp))
                    IconButton(
                        onClick = {
                            navController.popBackStack() // Vuelve a la pantalla anterior
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, // Usamos el icono de 'volver'
                            contentDescription = "Volver"
                        )
                    }
                }
            }
        }
    }
}
