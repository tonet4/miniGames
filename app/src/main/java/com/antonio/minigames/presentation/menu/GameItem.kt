package com.antonio.minigames.presentation.menu

import androidx.annotation.StringRes
import com.antonio.minigames.R
import com.antonio.minigames.domain.menu.model.MiniGame

data class GameItem(
    val game: MiniGame,
    @StringRes val titleRes: Int,
    val emoji: String,
)

fun MiniGame.toGameItem(): GameItem = when (this) {
    MiniGame.MEMORY -> GameItem(game = this, titleRes = R.string.game_memory, emoji = "🧠")
}
