package com.example.kineo.ui.theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.EmailAuthProvider

@Composable
fun ModificarPasswordScreen(onVolver: () -> Unit) {
    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Modificar la contrasena le mot de passe", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = actual, onValueChange = { actual = it },
            label = { Text("Mot de passe actuel") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = nueva, onValueChange = { nueva = it },
            label = { Text("Nouveau mot de passe") },
            visualTransformation = PasswordVisualTransformation()
        )

        mensaje?.let { Text(it) }

        Button(onClick = {
            val user = FirebaseAuth.getInstance().currentUser
            val email = user?.email
            if (user == null || email == null) return@Button
            val credencial = EmailAuthProvider.getCredential(email, actual)
            user.reauthenticate(credencial)
                .addOnSuccessListener {
                    user.updatePassword(nueva)
                        .addOnSuccessListener { mensaje = "Mot de passe modifié." }
                        .addOnFailureListener { mensaje = it.message }
                }
                .addOnFailureListener { mensaje = "Mot de passe actuel incorrect." }
        }) { Text("Valider") }

        TextButton(onClick = onVolver) { Text("Retour") }
    }
}