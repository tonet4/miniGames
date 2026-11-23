package com.antonio.minigames.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = RetroTeal,
    onPrimary = PaperLight,
    primaryContainer = RetroTealLight,
    onPrimaryContainer = InkPurple,
    secondary = RetroMustard,
    onSecondary = InkPurple,
    secondaryContainer = RetroMustardLight,
    onSecondaryContainer = InkPurple,
    tertiary = RetroTomato,
    onTertiary = PaperLight,
    tertiaryContainer = RetroTomatoLight,
    onTertiaryContainer = InkPurple,
    background = PaperCream,
    onBackground = InkPurple,
    surface = PaperLight,
    onSurface = InkPurple,
    surfaceVariant = PaperShade,
    onSurfaceVariant = InkSoft,
    outline = InkPurple,
    outlineVariant = InkSoft,
    error = RetroError,
    onError = PaperLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = ArcadeNight,
    primaryContainer = NeonCyanDark,
    onPrimaryContainer = ArcadeText,
    secondary = NeonYellow,
    onSecondary = ArcadeNight,
    secondaryContainer = NeonYellowDark,
    onSecondaryContainer = ArcadeText,
    tertiary = NeonPink,
    onTertiary = ArcadeNight,
    tertiaryContainer = NeonPinkDark,
    onTertiaryContainer = ArcadeText,
    background = ArcadeNight,
    onBackground = ArcadeText,
    surface = ArcadePanel,
    onSurface = ArcadeText,
    surfaceVariant = ArcadePanelHigh,
    onSurfaceVariant = ArcadeTextSoft,
    outline = ArcadeOutline,
    outlineVariant = ArcadePanelHigh,
    error = NeonRed,
    onError = ArcadeNight,
)

@Composable
fun MinigamesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
