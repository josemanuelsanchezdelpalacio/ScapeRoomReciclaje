package com.dam2jms.scaperoomreciclaje.navigation
sealed class AppScreens (val route: String){
    object InstruccionesScreen: AppScreens(route = "instrucciones_screen")

    object PreguntasScreen: AppScreens(route = "preguntas_screen")
}
