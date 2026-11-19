package com.example.kineo.data

interface Repositorio {
    fun ejercicios(): List<Ejercicio>
    fun ejercicio(id: Int): Ejercicio?
}

class RepositorioMemoria : Repositorio {
    private val lista = listOf(
        Ejercicio(1, "Flexión de rodilla", "10 repeticiones, 3 series.", hecho = true),
        Ejercicio(2, "Elevación de pierna", "Mantén 5 segundos, 10 veces."),
        Ejercicio(3, "Estiramiento de cuádriceps", "30 segundos por pierna.")
    )

    override fun ejercicios() = lista
    override fun ejercicio(id: Int) = lista.find { it.id == id }
}