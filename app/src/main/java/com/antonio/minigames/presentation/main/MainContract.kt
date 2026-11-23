package com.antonio.minigames.presentation.main

import com.antonio.minigames.domain.settings.entity.ThemeMode

data class MainUiState(
    val isLoading: Boolean = true,
    val themeMode: ThemeMode? = null,
)
