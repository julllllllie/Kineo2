package com.example.kineo.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.kineo.data.EjercicioAsignado
import com.example.kineo.data.EjerciciosRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource


@Composable
fun DashboardScreen(
    onPerfil: () -> Unit,
    onEjercicio: (String) -> Unit
) {
    var nombre by remember { mutableStateOf<String?>(null) }
    var ejercicios by remember { mutableStateOf<List<EjercicioAsignado>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            cargando = false
            return@LaunchedEffect
        }
        try {
            nombre = FirebaseFirestore.getInstance()
                .collection("users").document(uid)
                .get().await()
                .getString("nombre")
            ejercicios = EjerciciosRepository().getEjerciciosAsignados(uid)
        } catch (e: Exception) {
            Log.e("Dashboard", "Error de carga", e)
        }
        cargando = false
    }

    Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        if (cargando) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Saludo(nombre)
                    BotonPerfil(onPerfil = onPerfil)
                }

                Spacer(Modifier.height(24.dp))



                if (ejercicios.isEmpty()) {
                    Text("Ningún ejercicio asignado por el momento.")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(ejercicios, key = { it.asignacion.id }) { item ->
                            EjercicioCard(
                                item = item,
                                onClick = { onEjercicio(item.asignacion.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EjercicioCard(item: EjercicioAsignado, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val resId = rememberDrawableId(item.ejercicio.img.firstOrNull() ?: "")

            if (resId != 0) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = item.ejercicio.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(72.dp).clip(RoundedCornerShape(8.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(item.ejercicio.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    "${item.asignacion.sets} x ${item.asignacion.reps}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(item.ejercicio.categoria, style = MaterialTheme.typography.bodySmall)
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
        Text("Mi perfil")
    }
}

@Composable
fun rememberDrawableId(name: String): Int {
    val context = LocalContext.current
    return remember(name) {
        context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}

