package com.example.kineo.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

fun registrarUsuario(
    nombre: String,
    email: String,
    password: String,
    role : String,
    onExito: () -> Unit,
    onError: (String) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { result ->
            val uid = result.user?.uid ?: return@addOnSuccessListener
            val datos = hashMapOf<String, Any>(
                "nombre" to nombre,
                "email" to email,
                "role" to role
            )

            db.collection("users").document(uid)
                .set(datos)
                .addOnSuccessListener { onExito() }
                .addOnFailureListener { onError(it.message ?: "Error") }
        }
        .addOnFailureListener { onError(it.message ?: "Error") }
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