package com.example.finalprojectprogramacion.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalprojectprogramacion.viewmodel.ViewModelApp
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
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
fun MainApp(viewModelApp: ViewModelApp) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        // Pantalla de inicio
        composable("home") { HomeScreen(navController, viewModelApp) }

        // Pantalla del mapa
        composable("map") { MapScreen(viewModelApp, navController) }

        // Pantalla de discotecas
        composable("discs") { DiscScreen(viewModelApp, navController) }

        // Nueva pantalla de edición de reserva, con el ID de la discoteca como argumento
        composable(
            "editReserva/{discotecaId}",
            arguments = listOf(navArgument("discotecaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val discotecaId = backStackEntry.arguments?.getInt("discotecaId") ?: return@composable
            EditReservaScreen(viewModelApp = viewModelApp, navController = navController, discotecaId = discotecaId)
        }
    }
}
