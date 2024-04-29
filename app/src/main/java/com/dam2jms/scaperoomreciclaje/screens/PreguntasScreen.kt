package com.dam2jms.scaperoomreciclaje.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
            TopAppBar(
                title = { Text(text = "PREGUNTAS RECICLAJE") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    Text(text = "Intento ${uiState.preguntaActual + 1}/${preguntasRespuestas.size}")
                }
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

    //variable para controlar la abertura y cierre del menú
    var expanded by remember { mutableStateOf(false) }

    //variable para guardar la opción seleccionada
    var opcionSeleccionada by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        // Muestro la pregunta por pantalla
        Text(
            text = preguntasRespuestas.keys.elementAt(uiState.preguntaActual),
            fontSize = 24.sp
        )

        //creo la lista desplegable con las opciones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            Button(
                onClick = {
                    mvvm.anteriorPregunta()
                },
                enabled = uiState.preguntaActual > 0
            ) { Text(text = "Anterior") }
            Button(
                onClick = {
                    mvvm.siguientePregunta()
                },
                enabled = uiState.preguntaActual < preguntasRespuestas.size - 1
            ) { Text(text = "Siguiente") }
        }

        //si la pregunta actual es la última aparece el botón de comprobarRespuestas
        if (uiState.preguntaActual == preguntasRespuestas.size - 1) {
            Button(
                onClick = {
                    val respuestasCorrectas = mvvm.comprobarRespuestas()
                    // Muestro los AlertDialog independientemente de si todas son correctas o no
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
                onDismissRequest = {
                    mvvm.reiniciarJuego()
                    mostrarAlertDialog = false
                },
                title = {
                    //si todas las respuestas son correctas muestro el código secreto
                    //si no, muestro el número de preguntas acertadas
                    if (respuestasCorrectas == preguntasRespuestas.size) {
                        Text(text = "Enhorabuena el código secreto es ${mvvm.generarCodigo()}")
                    } else {
                        Text(text = "Tienes $respuestasCorrectas respuestas correctas")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        mvvm.reiniciarJuego()
                        mostrarAlertDialog = false
                    }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}
