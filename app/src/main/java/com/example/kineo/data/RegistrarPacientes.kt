package com.example.kineo.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

fun registrarPaciente(
    nombre: String,
    email: String,
    password: String,
    onExito: () -> Unit,
    onError: (String) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { result ->
            val uid = result.user?.uid
            if (uid == null) {
                onError("Error user")
                return@addOnSuccessListener
            }

            val paciente = hashMapOf(
                "nombre" to nombre,
                "email" to email,
                "creadoEn" to FieldValue.serverTimestamp()
            )

            db.collection("users")
                .document(uid)
                .set(paciente)
                .addOnSuccessListener { onExito() }
                .addOnFailureListener { onError(it.message ?: "Error Firestore") }
        }
        .addOnFailureListener { onError(it.message ?: "Error registro") }
}
fun iniciarSesion(
    email: String,
    password: String,
    onExito: () -> Unit,
    onError: (String) -> Unit
) {
    FirebaseAuth.getInstance()
        .signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { onExito() }
        .addOnFailureListener { onError(it.message ?: "Error conecion") }
}