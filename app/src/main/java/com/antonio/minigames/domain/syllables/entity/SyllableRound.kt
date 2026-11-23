package com.antonio.minigames.domain.syllables.entity

data class SyllableRound(
    val word: Word,
    val tiles: List<SyllableTile>,
    val placedCount: Int = 0,
) {
    val nextSyllable: String?
        get() = word.syllables.getOrNull(placedCount)

    val isFinished: Boolean
        get() = placedCount == word.syllables.size
}
