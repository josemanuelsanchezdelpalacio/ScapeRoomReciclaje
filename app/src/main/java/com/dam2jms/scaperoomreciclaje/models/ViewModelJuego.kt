package com.dam2jms.scaperoomreciclaje.models

import androidx.lifecycle.ViewModel
import com.dam2jms.scaperoomreciclaje.data.listaPreguntas
import com.dam2jms.scaperoomreciclaje.data.preguntasRespuestas
import com.dam2jms.scaperoomreciclaje.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class ViewModelJuego : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun seleccionarRespuesta(respuesta: String) {
        _uiState.value = _uiState.value.copy(respuestaSeleccionada = respuesta)
    }

    fun siguientePregunta() {
        if (_uiState.value.preguntaActual < preguntasRespuestas.size - 1) {
            _uiState.value = _uiState.value.copy(preguntaActual = _uiState.value.preguntaActual + 1)
        }
    }

    fun anteriorPregunta() {
        if (_uiState.value.preguntaActual > 0) {
            _uiState.value = _uiState.value.copy(preguntaActual = _uiState.value.preguntaActual - 1)
        }
    }

    fun comprobarRespuestas(): Boolean {
        val respuestaCorrecta = preguntasRespuestas.values.elementAt(_uiState.value.preguntaActual)
        return _uiState.value.respuestaSeleccionada == respuestaCorrecta
    }

    fun reiniciarJuego() {
        _uiState.value = UiState(preguntaActual = 0, respuestaSeleccionada = "")
    }

    fun generarCodigo(): Int {
        return (1000..9999).random()
    }
}
