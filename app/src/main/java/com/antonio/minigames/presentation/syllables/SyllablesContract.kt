package com.antonio.minigames.presentation.syllables

import com.antonio.minigames.domain.syllables.entity.SyllableRound

data class SyllablesUiState(
    val round: SyllableRound? = null,
    val wrongTileId: Int? = null,
)

sealed interface SyllablesIntent {
    data class TileTapped(val id: Int) : SyllablesIntent
    data object PhotoTapped : SyllablesIntent
    data object NextClicked : SyllablesIntent
    data object NavigateBack : SyllablesIntent
}

sealed interface SyllablesEffect {
    data class Speak(val text: String) : SyllablesEffect
    data object OnNavigateBack : SyllablesEffect
}
