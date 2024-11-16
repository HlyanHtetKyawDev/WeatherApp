package com.mm.weatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val LightGreenColorScheme = lightColorScheme(
    primary = LightGreenPrimary,
    onPrimary = LightGreenOnPrimary,
    primaryContainer = LightGreenPrimaryContainer,
    onPrimaryContainer = LightGreenOnPrimaryContainer,
    secondary = LightGreenSecondary,
    onSecondary = LightGreenOnSecondary,
    secondaryContainer = LightGreenSecondaryContainer,
    onSecondaryContainer = LightGreenOnSecondaryContainer,
    tertiary = LightGreenTertiary,
    onTertiary = LightGreenOnTertiary,
    tertiaryContainer = LightGreenTertiaryContainer,
    onTertiaryContainer = LightGreenOnTertiaryContainer,
    background = LightGreenBackground,
    onBackground = LightGreenOnBackground,
    surface = LightGreenSurface,
    onSurface = LightGreenOnSurface,
    error = LightGreenError,
    onError = LightGreenOnError,
    errorContainer = LightGreenErrorContainer,
    onErrorContainer = LightGreenOnErrorContainer,
    outline = LightGreenOutline,
    surfaceVariant = LightGreenSurfaceVariant,
    onSurfaceVariant = LightGreenOnSurfaceVariant
)


@Composable
fun WeatherAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightGreenColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}