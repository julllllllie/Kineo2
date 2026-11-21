package com.example.kineo.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EjerciciosRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getEjerciciosAsignados(uid: String): List<EjercicioAsignado> {
        val asignaciones = db.collection("users").document(uid)
            .collection("asignaciones")
            .whereEqualTo("status", "active")
            .get().await()
            .toObjects(Asignacion::class.java)

        return asignaciones.mapNotNull { a ->
            val ejercicio = db.collection("ejercicios")
                .document(a.exerciseId)
                .get().await()
                .toObject(Ejercicio::class.java)
            ejercicio?.let { EjercicioAsignado(a, it) }
        }
    }
    suspend fun getEjercicioAsignado(uid: String, asignacionId: String): EjercicioAsignado? {
        val asignacion = db.collection("users").document(uid)
            .collection("asignaciones").document(asignacionId)
            .get().await()
            .toObject(Asignacion::class.java) ?: return null

        val ejercicio = db.collection("ejercicios").document(asignacion.exerciseId)
            .get().await()
            .toObject(Ejercicio::class.java) ?: return null

        return EjercicioAsignado(asignacion, ejercicio)
    }
}