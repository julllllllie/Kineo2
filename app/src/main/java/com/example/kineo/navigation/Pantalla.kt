package com.example.kineo.navigation

sealed interface Pantalla {
    data object Inicio : Pantalla
    data object Registro : Pantalla
    data object Login : Pantalla
    data object Dashboard : Pantalla

    data class Detalle(val asignacionId: String) : Pantalla
    data object Perfil : Pantalla
    data object ModificarPassword : Pantalla
}