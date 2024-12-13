package com.example.finalprojectprogramacion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex


val GoogleSat: OnlineTileSourceBase = object : XYTileSource(
    "Google-Sat",
    0, 19, 256, ".png", arrayOf(
        "https://mt0.google.com",
        "https://mt1.google.com",
        "https://mt2.google.com",
        "https://mt3.google.com",
    )
) {
    override fun getTileURLString(pTileIndex: Long): String {
        return baseUrl + "/vt/lyrs=s&x=" + MapTileIndex.getX(pTileIndex) + "&y=" + MapTileIndex.getY(
            pTileIndex
        ) + "&z=" + MapTileIndex.getZoom(pTileIndex)
    }
}
@Composable
fun MainApp(ViewModelApp: ViewModelApp) {
    val discotecas by ViewModelApp.discotecas.collectAsState()
    val tiposdiscotecas by ViewModelApp.tiposdiscotecas.collectAsState()

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(28.964967918770267, -13.553658852564096)
        zoom = 13.5
    }

    var mapProperties by remember { mutableStateOf(DefaultMapProperties) }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties
    ) {
        mapProperties = mapProperties
            .copy(tileSources = com.example.finalprojectprogramacion.GoogleSat)
            .copy(isEnableRotationGesture = true)

        discotecas.forEach { discoteca ->
            val discotecaState = rememberMarkerState(
                geoPoint = GeoPoint(
                    discoteca.latitude.toDouble(),
                    discoteca.longitude.toDouble()
                )
            )

            val nombreTipoDiscoteca = tiposdiscotecas[discoteca.IdTipoDiscoteca]

            val context = LocalContext.current

            val ImagenLugares = when (discoteca.IdTipoDiscoteca) {
                1-> R.drawable.rock
                2-> R.drawable.reggaeton
                3-> R.drawable.salsa
                else -> R.drawable.variado
            }

            val drawable = ContextCompat.getDrawable(context, ImagenLugares)

            Marker(
                state = discotecaState,
                title = discoteca.name,
                snippet = nombreTipoDiscoteca,
                icon = drawable
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = Color(0x9A3044B2),
                            shape = RoundedCornerShape(40.dp),

                            )
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
                    IconButton(
                        onClick = {  }
                    ){
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}