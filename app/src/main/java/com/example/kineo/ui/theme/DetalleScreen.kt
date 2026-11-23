package com.example.kineo.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.kineo.data.EjercicioAsignado
import com.example.kineo.data.EjerciciosRepository
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun DetalleScreen(asignacionId: String, onBack: () -> Unit) {
    var item by remember { mutableStateOf<EjercicioAsignado?>(null) }
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(asignacionId) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            try {
                item = EjerciciosRepository().getEjercicioAsignado(uid, asignacionId)
            } catch (e: Exception) {
                Log.e("Detalle", "Error de carga", e)
            }
        }
        cargando = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Retour") }

        when {
            cargando -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            item == null -> Text("Exercice introuvable.")
            else -> DetalleContenido(item!!)
        }
    }
}

@Composable
private fun DetalleContenido(item: EjercicioAsignado) {
    val ej = item.ejercicio
    val asig = item.asignacion

    // Titre
    Text(ej.title, style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.height(16.dp))

    // Vidéo
    val videoId = extractYouTubeId(ej.videoUrl)
    if (videoId != null) {
        YouTubePlayer(
            videoId = videoId,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )
    } else {
        Text("Vidéo indisponible.", style = MaterialTheme.typography.bodyMedium)
    }
    Spacer(Modifier.height(16.dp))

    // Description
    Text(ej.descripcion, style = MaterialTheme.typography.bodyLarge)
    Spacer(Modifier.height(24.dp))

    // Programme (depuis asignaciones)
    Text("Programme", style = MaterialTheme.typography.titleLarge)
    Spacer(Modifier.height(8.dp))
    Text("Séries : ${asig.sets}")
    Text("Répétitions : ${asig.reps}")
    if (asig.time>0) Text("Temps : ${asig.time}")
    if (asig.notes.isNotBlank()) {
        Spacer(Modifier.height(12.dp))
        Text("Notes", style = MaterialTheme.typography.titleMedium)
        Text(asig.notes)
    }
    Spacer(Modifier.height(24.dp))

    // Image(s) en bas
    val resId = rememberDrawableId(ej.img)
    if (resId != 0) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = ej.title,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Composable
fun YouTubePlayer(videoId: String, modifier: Modifier = Modifier) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            }
        }
    )
}

fun extractYouTubeId(url: String): String? =
    Regex("(?:youtu\\.be/|v=|embed/|shorts/)([A-Za-z0-9_-]{11})")
        .find(url)?.groupValues?.get(1)