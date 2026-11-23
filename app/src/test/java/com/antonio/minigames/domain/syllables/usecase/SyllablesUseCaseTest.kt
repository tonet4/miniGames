package com.antonio.minigames.domain.syllables.usecase

import com.antonio.minigames.domain.syllables.entity.SyllableRound
import com.antonio.minigames.domain.syllables.entity.Word
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SyllablesUseCaseTest {

    private val useCase = SyllablesUseCase()

    @Test
    fun `syllables of every word join into its name`() {
        assertTrue(Word.entries.all { it.syllables.joinToString("") == it.name })
    }

    @Test
    fun `shuffled words contain every word once`() {
        assertEquals(Word.entries.toSet(), useCase.getShuffledWords().toSet())
        assertEquals(Word.entries.size, useCase.getShuffledWords().size)
    }

    @Test
    fun `createRound has the syllables plus one that is not in the word`() {
        val texts = useCase.createRound(Word.GATO).tiles.map { it.text }

        assertEquals(3, texts.distinct().size)
        assertTrue(texts.containsAll(listOf("GA", "TO")))
    }

    @Test
    fun `placeTile places the next syllable`() {
        val result = place(useCase.createRound(Word.GATO), "GA")

        assertEquals("TO", result.nextSyllable)
        assertTrue(result.tiles.first { it.text == "GA" }.isPlaced)
    }

    @Test
    fun `placeTile ignores a wrong syllable and an unknown tile`() {
        val round = useCase.createRound(Word.GATO)

        assertEquals(round, place(round, "TO"))
        assertEquals(round, useCase.placeTile(round, 99))
    }

    @Test
    fun `round is finished when every syllable is placed`() {
        val halfway = place(useCase.createRound(Word.GATO), "GA")

        assertFalse(halfway.isFinished)
        assertTrue(place(halfway, "TO").isFinished)
    }

    private fun place(round: SyllableRound, text: String): SyllableRound =
        useCase.placeTile(round, round.tiles.first { it.text == text }.id)
}
