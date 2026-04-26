package com.leogcc.cinemix.data.model

data class Item(
    val id: String = "",
    val tipo: String = "",
    val titulo: String = "",
    val portadaUrl: String = "",
    val anio: Int = 0,
    val genero: String = "",
    val calificacion: Float = 0f,
    val esFavorito: Boolean = false,
    val vecesVisto: Int = 0,
    val estado: String = "",
    val director: String = "",
    val autor: String = "",
    val actores: List<String> = emptyList(),
    val temporadas: Int = 0,
    val paginas: Int = 0,
    val sinopsis: String = "",
    val userId: String = ""
)
