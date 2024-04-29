package com.dam2jms.scaperoomreciclaje.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2jms.scaperoomreciclaje.navigation.AppScreens
import kotlin.system.exitProcess

@Composable
fun InstruccionesScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tarjeta donde aparecen las instrucciones
        Card(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Instrucciones del juego:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Aparecerán preguntas con opciones para seleccionar.")
                Text(text = "Si se han respondido bien todas las preguntas se te dará el código secreto.")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para entrar al juego
        Button(
            onClick = { navController.navigate(route = AppScreens.PreguntasScreen.route) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Blue, contentColor = Color.White)
        ) {
            Text("Entrar al juego")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para salir del juego
        Button(
            onClick = { exitProcess(0) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Blue, contentColor = Color.White)
        ) {
            Text("Salir")
        }
    }
}

