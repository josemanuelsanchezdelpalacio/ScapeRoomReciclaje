package com.dam2jms.scaperoomreciclaje.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2jms.scaperoomreciclaje.data.preguntasRespuestas
import com.dam2jms.scaperoomreciclaje.models.ViewModelJuego
import com.dam2jms.scaperoomreciclaje.navigation.AppScreens
import com.dam2jms.scaperoomreciclaje.states.UiState
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
