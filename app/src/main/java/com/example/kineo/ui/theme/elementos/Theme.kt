package com.example.kineo.ui.theme.elementos

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = KineoPrimary,
    onPrimary = KineoOnPrimary,
    primaryContainer = KineoSurfaceVariant,
    onPrimaryContainer = KineoTextPrimary,
    secondary = KineoSecondary,
    onSecondary = KineoOnPrimary,
    secondaryContainer = KineoSurfaceVariant,
    onSecondaryContainer = KineoTextPrimary,
    tertiary = KineoPrimaryDark,
    background = KineoBackground,
    onBackground = KineoTextPrimary,
    surface = KineoSurface,
    onSurface = KineoTextPrimary,
    surfaceVariant = KineoSurfaceVariant,
    onSurfaceVariant = KineoTextSecondary,
    error = KineoError,
    onError = KineoOnPrimary
)

@Composable
fun KineoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}