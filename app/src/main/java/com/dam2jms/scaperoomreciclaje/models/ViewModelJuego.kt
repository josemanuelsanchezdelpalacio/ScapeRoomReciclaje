package com.dam2jms.scaperoomreciclaje.models

import androidx.lifecycle.ViewModel
import com.dam2jms.scaperoomreciclaje.data.preguntasRespuestas
import com.dam2jms.scaperoomreciclaje.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelJuego : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _respuestasUsuario = MutableStateFlow(mutableMapOf<Int, String>())
    val respuestasUsuario: StateFlow<MutableMap<Int, String>> = _respuestasUsuario

    //para seleccionar la opcion y guardar la respuesta del usuario
    fun seleccionarRespuesta(respuesta: String) {
        _uiState.value = _uiState.value.copy(respuestaSeleccionada = respuesta)
        _respuestasUsuario.value[_uiState.value.preguntaActual] = respuesta
    }

    fun siguientePregunta() {
        //si no es la ultima pregunta pasa a la siguiente
        if (_uiState.value.preguntaActual < preguntasRespuestas.size - 1) {
            _uiState.value = _uiState.value.copy(preguntaActual = _uiState.value.preguntaActual + 1)
        }
    }

    fun anteriorPregunta() {
        if (_uiState.value.preguntaActual > 0) {
            _uiState.value = _uiState.value.copy(preguntaActual = _uiState.value.preguntaActual - 1)
        }
    }

    fun comprobarRespuestas(): Int {
        var respuestasCorrectas = 0

        //recorro las respuestas del usuario
        _respuestasUsuario.value.forEach { (preguntaIndex, respuesta) ->
            //guardo la pregunta actual
            val pregunta = preguntasRespuestas.keys.elementAt(preguntaIndex)
            //si la respuesta de la pregunta actual es igual a la respuesta del usuario es correcta
            if (preguntasRespuestas[pregunta] == respuesta) {
                //aumento el contador de respuestas correctas
                respuestasCorrectas++
            }
        }
        return respuestasCorrectas
    }

    fun reiniciarJuego() {
        _uiState.value = UiState(preguntaActual = 0, respuestaSeleccionada = "")
        _respuestasUsuario.value = mutableMapOf()
    }

    fun generarCodigo(): Int {
        return (1000..9999).random()
    }
}
