package com.thejasvee.coolblue.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val EmptyOrangeBackgroundLight = Color(0xFFFFE4BD)
val EmptyOrangeBackgroundDark = Color(0xFF3A2A1B)

val EmptyGreyBackgroundLight = Color(0xFFEDEFF3)
val EmptyGreyBackgroundDark = Color(0xFF252C36)
val LightMutedText = Color(0xFF9CA3AF)
val DarkMutedText = Color(0xFF9CA3AF)


@Composable
fun initialSearchIconBackgroundColor(): Color {
    return if (isSystemInDarkTheme()) {
        EmptyOrangeBackgroundDark
    } else {
        EmptyOrangeBackgroundLight
    }
}

@Composable
fun noResultsIconBackgroundColor(): Color {
    return if (isSystemInDarkTheme()) {
        EmptyGreyBackgroundDark
    } else {
        EmptyGreyBackgroundLight
    }
}


@Composable
fun mutedTextColor(): Color {
    return if (isSystemInDarkTheme()) {
        DarkMutedText
    } else {
        LightMutedText
    }
}