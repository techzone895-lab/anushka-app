package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val RomanticColorScheme = lightColorScheme(
    primary = DeepRose,
    secondary = RomanticPink,
    tertiary = WarmGold,
    background = SoftBlush,
    surface = SoftWhite,
    onPrimary = SoftWhite,
    onSecondary = SoftWhite,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RomanticColorScheme,
        typography = Typography,
        content = content
    )
}
