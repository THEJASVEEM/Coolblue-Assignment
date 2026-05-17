package com.thejasvee.coolblue.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = CoolbluePrimary,
    onPrimary = LightSurface,

    secondary = OrangePrimary,
    onSecondary = LightSurface,

    background = LightBackground,
    onBackground = LightPrimaryText,

    surface = LightSurface,
    onSurface = LightPrimaryText,

    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightSecondaryText,

    outline = LightBorder,

    error = Error,
    onError = LightSurface
)

private val DarkColorScheme = darkColorScheme(
    primary = CoolbluePrimary,
    onPrimary = DarkPrimaryText,

    secondary = OrangePrimary,
    onSecondary = DarkPrimaryText,

    background = DarkBackground,
    onBackground = DarkPrimaryText,

    surface = DarkSurface,
    onSurface = DarkPrimaryText,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkSecondaryText,

    outline = DarkBorder,

    error = Error,
    onError = DarkPrimaryText
)

@Composable
fun CoolblueTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}