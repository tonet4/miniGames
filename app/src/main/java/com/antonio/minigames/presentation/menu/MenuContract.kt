package com.antonio.minigames.presentation.menu

import com.antonio.minigames.domain.menu.entity.MiniGame
import com.antonio.minigames.domain.settings.entity.ThemeMode

data class MenuUiState(
    val games: List<GameItem> = emptyList(),
    val themeMode: ThemeMode? = null,
)

sealed interface MenuIntent {
    data class GameClicked(val game: MiniGame) : MenuIntent
    data class ThemeSelected(val themeMode: ThemeMode) : MenuIntent
}

sealed interface MenuEffect {
    data class OpenGame(val game: MiniGame) : MenuEffect
}
