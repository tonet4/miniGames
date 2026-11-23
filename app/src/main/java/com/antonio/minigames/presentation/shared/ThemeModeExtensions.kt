package com.antonio.minigames.presentation.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.antonio.minigames.domain.settings.entity.ThemeMode

@Composable
fun ThemeMode?.isDark(): Boolean = when (this) {
    ThemeMode.LIGHT -> false
    ThemeMode.DARK -> true
    null -> isSystemInDarkTheme()
}
