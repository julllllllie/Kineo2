package com.example.kineo.data

import com.google.firebase.firestore.DocumentId

data class Paciente(val nombre: String, val email: String, val role: String)

data class Ejercicio(
    @DocumentId val id: String = "",
    val title: String = "",
    val descripcion: String = "",
    val categoria: String = "",
    val img: List<String> = emptyList(),
    val videoUrl: String = ""
)

data class Asignacion(
    @DocumentId val id: String = "",
    val exerciseId: String = "",
    val sets: Int = 0,
    val reps: Int = 0,
    val time: Int = 0,
    val notes: String = "",
    val status: String = "active"
)

data class EjercicioAsignado(
    val asignacion: Asignacion,
    val ejercicio: Ejercicio
)