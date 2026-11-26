package com.example.kineo.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.kineo.ui.theme.BotonGrande

@Composable
fun PerfilScreen(onCambiarPassword: () -> Unit, onVolver: () -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    var nombre by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).get()
                .addOnSuccessListener { nombre = it.getString("nombre") }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mon profil", style = MaterialTheme.typography.headlineMedium)
        Text("Nom : ${nombre ?: "..."}")
        Text("Email : ${user?.email ?: "..."}")

        BotonGrande(texto = "Cambiar la contraseña", onClick = onCambiarPassword)
        TextButton(onClick = onVolver) { Text("Retour") }
    }
}