package com.example.kineo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.kineo.ui.theme.InicioScreen
import com.example.kineo.ui.theme.elementos.KineoTheme
import com.example.kineo.navigation.Pantalla
import com.example.kineo.ui.*
import com.example.kineo.data.*

import com.example.kineo.data.iniciarSesion
import com.example.kineo.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KineoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var pantalla by remember { mutableStateOf<Pantalla>(Pantalla.Inicio) }
                    var errorRegistro by remember { mutableStateOf<String?>(null) }
                    var errorLogin by remember { mutableStateOf<String?>(null) }

                    when (val p = pantalla) {
                        Pantalla.Inicio -> InicioScreen(
                            onRegistro = { pantalla = Pantalla.Registro },
                            onRegistroMedico = { pantalla = Pantalla.RegistroMedico },
                            onLogin = { pantalla = Pantalla.Login }
                        )
                        Pantalla.Registro -> RegistroScreen(
                            error = errorRegistro,
                            esMedico = false,
                            onCrearCuenta = { nombre, email, password, role ->
                                registrarUsuario(
                                    nombre, email, password,role,
                                    onExito = {
                                        errorRegistro = null
                                        pantalla = Pantalla.Dashboard
                                    },
                                    onError = { errorRegistro = it }
                                )
                            },
                            onVolver = { pantalla = Pantalla.Inicio }
                        )
                        Pantalla.RegistroMedico -> RegistroScreen(
                            error = errorRegistro,
                            esMedico = true,
                            onCrearCuenta = { nombre, email, password, role ->
                                registrarUsuario(
                                    nombre, email, password, role,
                                    onExito = {
                                        errorRegistro = null
                                        pantalla = Pantalla.DashboardMedico
                                    },
                                    onError = { errorRegistro = it }
                                )
                            },
                            onVolver = { pantalla = Pantalla.Inicio }
                        )

                        Pantalla.Login -> LoginScreen(
                            error = errorLogin,
                            onAceptar = { email, password ->
                                iniciarSesion(
                                    email, password,
                                    onExito = { errorLogin = null; pantalla = Pantalla.Dashboard },
                                    onError = { errorLogin = it }
                                )
                            },
                            onVolver = { pantalla = Pantalla.Inicio }
                        )

                        Pantalla.Dashboard -> DashboardScreen(
                            onPerfil = { pantalla = Pantalla.Perfil },
                            onEjercicio = { id -> pantalla = Pantalla.Detalle(id) }
                        )

                        is Pantalla.Detalle -> DetalleScreen(
                            asignacionId = p.asignacionId,
                            onBack = { pantalla = Pantalla.Dashboard }
                        )

                        Pantalla.Perfil -> PerfilScreen(
                            onCambiarPassword = { pantalla = Pantalla.ModificarPassword },
                            onVolver = { pantalla = Pantalla.Dashboard }
                        )

                        Pantalla.ModificarPassword -> ModificarPasswordScreen(
                            onVolver = { pantalla = Pantalla.Perfil }
                        )
                    else -> { }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KineoTheme {
        Greeting("Android")
    }
}