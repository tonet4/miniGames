package com.antonio.minigames.domain.syllables.usecase

import com.antonio.minigames.domain.syllables.entity.SyllableRound
import com.antonio.minigames.domain.syllables.entity.SyllableTile
import com.antonio.minigames.domain.syllables.entity.Word
import javax.inject.Inject
import kotlin.random.Random

class SyllablesUseCase @Inject constructor() {

    fun getShuffledWords(random: Random = Random.Default): List<Word> =
        Word.entries.shuffled(random)

    fun createRound(word: Word, random: Random = Random.Default): SyllableRound {
        val extra = Word.entries
            .flatMap { it.syllables.toList() }
            .distinct()
            .filter { it !in word.syllables }
            .random(random)
        val tiles = (word.syllables.toList() + extra)
            .shuffled(random)
            .mapIndexed { index, text -> SyllableTile(id = index, text = text) }
        return SyllableRound(word = word, tiles = tiles)
    }

    fun placeTile(round: SyllableRound, tileId: Int): SyllableRound {
        val tile = round.tiles.find { it.id == tileId } ?: return round
        if (tile.isPlaced || tile.text != round.nextSyllable) return round
        return round.copy(
            tiles = round.tiles.map { if (it.id == tileId) it.copy(isPlaced = true) else it },
            placedCount = round.placedCount + 1,
        )
    }
}
