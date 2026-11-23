package com.example.kineo.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kineo.data.Ejercicio

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
fun BotonGrande(texto: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth().height(56.dp)) {
        Text(texto)
    }
}

@Composable
fun InicioScreen(onRegistro: () -> Unit, onLogin: () -> Unit) =
    EsqueletoPantalla("Kineo") {
        BotonGrande("Registro", onRegistro)
        BotonGrande("Iniciar sesión", onLogin)
    }

@Composable
fun RegistroScreen(
    onCrear: (String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    EsqueletoPantalla("Registro") {

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        BotonGrande("Crear cuenta") {
            onCrear(nombre, email)
        }
    }
}

//@Composable
//fun LoginScreen(onAceptar: () -> Unit) =
//    EsqueletoPantalla("Iniciar sesión") {
//        BotonGrande("Aceptar", onAceptar)
//    }

//@Composable
//fun DashboardScreen(
//    ejercicios: List<Ejercicio>,
//    onPerfil: () -> Unit,
//    onDetalle: (Int) -> Unit
//) = EsqueletoPantalla("Panel de control") {
//    BotonGrande("Perfil", onPerfil)
//    val progreso = ejercicios.count { it.hecho } / ejercicios.size.toFloat()
//    Text("Progreso de hoy: ${(progreso * 100).toInt()}%")
//    LinearProgressIndicator(progress = { progreso }, modifier = Modifier.fillMaxWidth())
//    ejercicios.forEach { BotonGrande(it.nombre) { onDetalle(it.id) } }
//}

//@Composable
//fun DetalleScreen(ejercicio: Ejercicio?, onVolver: () -> Unit) =
  //  EsqueletoPantalla(ejercicio?.nombre ?: "Ejercicio no encontrado") {
    //    Text(ejercicio?.instrucciones ?: "")
      //  BotonGrande("Volver", onVolver)
//    }

//@Composable
//fun PerfilScreen(onVolver: () -> Unit, onModificar: () -> Unit) =
  //  EsqueletoPantalla("Perfil") {
    //    BotonGrande("Modificar contraseña", onModificar)
      //  BotonGrande("Volver", onVolver)
//    }

//@Composable
//fun ModificarScreen(onConfirmar: () -> Unit) =
  //  EsqueletoPantalla("Modificar contraseña") {
    //    BotonGrande("Confirmar", onConfirmar)
//    }