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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dam2jms.scaperoomreciclaje.data.preguntasRespuestas
import com.dam2jms.scaperoomreciclaje.models.ViewModelJuego
import com.dam2jms.scaperoomreciclaje.states.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreguntasScreen(navController: NavController, mvvm: ViewModelJuego) {

    val uiState by mvvm.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "PREGUNTAS RECICLAJE") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        PreguntasBodyScreen(modifier = Modifier.padding(paddingValues), mvvm, uiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreguntasBodyScreen(modifier: Modifier, mvvm: ViewModelJuego, uiState: UiState) {
    val context = LocalContext.current
    var mostrarAlertDialog by rememberSaveable { mutableStateOf(false) }

    //variable para controlar la abertura y cierre del menu
    var expanded by remember { mutableStateOf(false) }

    //variable para guardar la opcion seleccionada
    var opcionSeleccionada by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        //muestro la pregunta por pantalla
        Text(
            text = preguntasRespuestas.keys.elementAt(uiState.preguntaActual),
            fontSize = 24.sp
        )

        //creo la lista desplegable con las opciones
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                TextField(
                    value = uiState.respuestaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = !expanded }) {
                    uiState.listaOpciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(text = opcion) },
                            onClick = {
                                opcionSeleccionada = opcion
                                mvvm.seleccionarRespuesta(opcion)
                                expanded = false
                                Toast.makeText(
                                    context,
                                    "Has elegido la $opcion",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { mvvm.anteriorPregunta() }) { Text(text = "Anterior") }
            Button(onClick = { mvvm.siguientePregunta() }) { Text(text = "Siguiente") }
        }

        if (uiState.preguntaActual == preguntasRespuestas.size - 1) {
            Button(
                onClick = {
                    // Aplica el método comprobarRespuestas cuando se hace clic en el botón
                    val respuestasCorrectas = mvvm.comprobarRespuestas()
                    if (respuestasCorrectas == preguntasRespuestas.size) {
                        mostrarAlertDialog = true
                    } else {
                        mostrarAlertDialog = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Comprobar respuestas")
            }
        }

        if (mostrarAlertDialog) {
            val respuestasCorrectas = mvvm.comprobarRespuestas()
            AlertDialog(
                onDismissRequest = { mostrarAlertDialog = false },
                title = { Text(text = "Resultado") },
                text = {
                    if (respuestasCorrectas == preguntasRespuestas.size) {
                        Text(text = "Enhorabuena el código secreto es ${mvvm.generarCodigo()}")
                    } else {
                        Text(text = "Tienes $respuestasCorrectas respuestas correctas. Inténtalo de nuevo.")
                    }
                },
                confirmButton = {
                    Button(onClick = { mostrarAlertDialog = false }) {
                        Text(text = "OK")
                    }
                }
            )
        }

    }
}