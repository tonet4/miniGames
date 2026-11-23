package com.antonio.minigames.presentation.menu

import androidx.annotation.StringRes
import com.antonio.minigames.R
import com.antonio.minigames.domain.menu.entity.MiniGame

private const val MEMORY_COVER_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"

data class GameItem(
    val game: MiniGame,
    @StringRes val titleRes: Int,
    val coverUrl: String,
)

fun MiniGame.toGameItem(): GameItem = when (this) {
    MiniGame.MEMORY -> GameItem(
        game = this,
        titleRes = R.string.game_memory,
        coverUrl = MEMORY_COVER_URL,
    )
}
