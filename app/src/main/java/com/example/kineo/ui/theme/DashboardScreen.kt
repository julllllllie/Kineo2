package com.example.kineo.ui.theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun DashboardScreen(onPerfil: () -> Unit) {
    var nombre by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            cargando = false
            return@LaunchedEffect
        }
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                nombre = doc.getString("nombre")
                cargando = false
            }
            .addOnFailureListener { cargando = false }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        if (cargando) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Saludo(nombre)
                BotonPerfil(onPerfil = onPerfil)
            }
        }
    }


}

@Composable
fun Saludo(nombre: String?) {
    Text(
        text = "Bonjour, ${nombre ?: "utilisateur"} !",
        style = MaterialTheme.typography.headlineMedium
    )
}
@Composable
fun BotonPerfil(onPerfil: () -> Unit) {
    Button(onClick = onPerfil) {
        Text("Mon profil")
    }
}

