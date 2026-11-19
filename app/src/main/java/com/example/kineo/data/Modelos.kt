package com.example.kineo.data

data class Paciente(val nombre: String, val email: String, val medico: String)

data class Ejercicio(
    val id: Int,
    val nombre: String,
    val instrucciones: String,
    val hecho: Boolean = false
)