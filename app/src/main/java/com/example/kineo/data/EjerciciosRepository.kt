package com.example.kineo.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log

class EjerciciosRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getEjerciciosAsignados(uid: String): List<EjercicioAsignado> {
        Log.d("Kineo", "A) fonction appelée, uid=$uid")
        val asignaciones = db.collection("users").document(uid)
            .collection("asignaciones")
            .whereEqualTo("status", "active")
            .get().await()
            .toObjects(Asignacion::class.java)

        Log.d("Kineo", "NB asignaciones = ${asignaciones.size}")   // <- ligne 1
        Log.d("Kineo", "asignaciones = $asignaciones")

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