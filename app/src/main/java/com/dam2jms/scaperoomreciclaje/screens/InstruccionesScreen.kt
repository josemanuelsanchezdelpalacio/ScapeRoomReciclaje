package com.dam2jms.scaperoomreciclaje.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2jms.scaperoomreciclaje.navigation.AppScreens
import kotlin.system.exitProcess

@Composable
fun InstruccionesScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        // Tarjeta donde aparecen las instrucciones
        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Instrucciones del juego:")
                Text(text = "Apareceran preguntas con opciones para seleccionar.")
                Text(text = "Si se han respondido bien todas las preguntas se te dara el codigo secreto")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para entrar al juego
        Button(onClick = { navController.navigate(route = AppScreens.PreguntasScreen.route) }) {
            Text("Entrar al juego")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para salir del juego
        Button(onClick = { exitProcess(0) }) {
            Text("Salir")
        }
    }
}
