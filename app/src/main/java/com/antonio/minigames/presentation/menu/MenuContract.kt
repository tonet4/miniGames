package com.antonio.minigames.presentation.menu

import com.antonio.minigames.domain.menu.model.MiniGame

data class MenuUiState(
    val games: List<GameItem> = emptyList(),
)

sealed interface MenuIntent {
    data class GameClicked(val game: MiniGame) : MenuIntent
}

sealed interface MenuEffect {
    data class OpenGame(val game: MiniGame) : MenuEffect
}
