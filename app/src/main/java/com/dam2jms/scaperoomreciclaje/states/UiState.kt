package com.dam2jms.scaperoomreciclaje.states

data class UiState(
    val listaOpciones : List<String> = listOf("Contenedor azul", "Contenedor verde", "Contenedor amarillo", "Contenedor organico", "Punto limpio"),
    var preguntaActual: Int = 0,
    var respuestaSeleccionada: String = "",
    val mostrarDialogo: Boolean = false
)
