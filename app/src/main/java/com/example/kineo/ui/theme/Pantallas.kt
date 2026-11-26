package com.example.kineo.ui.theme

import com.example.kineo.R

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight



@Composable
fun EsqueletoPantalla(titulo: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(titulo, style = MaterialTheme.typography.headlineMedium)
        content()
    }
}
@Composable
fun InicioScreen(onRegistro: () -> Unit, onLogin: () -> Unit, onRegistroMedico: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Photo de fond
        Image(
            painter = painterResource(R.drawable.logo_kineo_fond),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black.copy(alpha = 0.25f), Color.Black.copy(alpha = 0.65f))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.logo_kineo_general),
                contentDescription = "Logo Kineo",
                modifier = Modifier.align(Alignment.Start)
                    .padding(top = 16.dp)
                    .size(72.dp)
            )

            Spacer(Modifier.weight(1.2f))

            BotonGrande("Registro", onRegistro)

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("Iniciar sesión", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onRegistroMedico) { Text("Soy medico") }

            Spacer(Modifier.height(48.dp))
        }
    }
}
@Composable
fun BotonGrande(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(texto, fontWeight = FontWeight.Bold)
    }
}


//@Composable
//fun RegistroScreen(
//    onCrear: (String, String) -> Unit
//) {
//    var nombre by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//
//    EsqueletoPantalla("Registro") {
//
//        OutlinedTextField(
//            value = nombre,
//            onValueChange = { nombre = it },
//            label = { Text("Nombre") }
//        )
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") }
//        )
//
//        BotonGrande("Crear cuenta") {
//            onCrear(nombre, email)
//        }
//    }
//}

