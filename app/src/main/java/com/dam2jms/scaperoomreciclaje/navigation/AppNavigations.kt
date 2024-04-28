package com.dam2jms.scaperoomreciclaje.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2jms.scaperoomreciclaje.models.ViewModelJuego
import com.dam2jms.scaperoomreciclaje.screens.InstruccionesScreen
import com.dam2jms.scaperoomreciclaje.screens.PreguntasScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.InstruccionesScreen.route) {
        composable(route = AppScreens.InstruccionesScreen.route) { InstruccionesScreen(navController) }
        composable(route = AppScreens.PreguntasScreen.route) { PreguntasScreen(navController, mvvm = ViewModelJuego()) }
    }
}
