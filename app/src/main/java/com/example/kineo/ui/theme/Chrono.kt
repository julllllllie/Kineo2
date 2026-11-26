package com.example.kineo.ui.theme

import android.os.SystemClock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Chrono(modifier: Modifier = Modifier) {
    var elapsedMs by rememberSaveable { mutableLongStateOf(0L) }
    var running by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(running) {
        if (running) {
            val start = SystemClock.elapsedRealtime() - elapsedMs
            while (true) {
                elapsedMs = SystemClock.elapsedRealtime() - start
                delay(50)
            }
        }
    }

    val totalSec = elapsedMs / 1000
    val text = "%02d:%02d".format(totalSec / 60, totalSec % 60)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { running = !running }) {
                Text(if (running) "Pause" else "Play")
            }
            OutlinedButton(
                onClick = { running = false; elapsedMs = 0L },
                enabled = elapsedMs > 0L
            ) {
                Text("Reset")
            }
        }
    }
}