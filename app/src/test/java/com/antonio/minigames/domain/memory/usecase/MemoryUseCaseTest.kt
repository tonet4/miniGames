package com.antonio.minigames.domain.memory.usecase

import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.memory.entity.CardState
import com.antonio.minigames.domain.memory.entity.MemoryBoard
import com.antonio.minigames.domain.memory.entity.MemoryCard
import com.antonio.minigames.domain.memory.entity.MemoryItem
import com.antonio.minigames.domain.pokemon.usecase.PokemonUseCase
import com.antonio.minigames.testutil.FakePokemonRepository
import kotlin.random.Random
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MemoryUseCaseTest {

    private val repository = FakePokemonRepository()
    private val useCase = MemoryUseCase(PokemonUseCase(repository))
    private val itemA = item("a")
    private val itemB = item("b")
    private val board = MemoryBoard(
        cards = listOf(itemA, itemA, itemB, itemB)
            .mapIndexed { index, item -> MemoryCard(id = index, item = item) },
    )

    @Test
    fun `createRandomBoard creates the requested number of pairs`() = runTest {
        val result = useCase.createRandomBoard(4).getOrThrow()

        assertEquals(8, result.cards.size)
        assertEquals(4, result.cards.distinctBy { it.item.id }.size)
    }

    @Test
    fun `createRandomBoard returns the repository error`() = runTest {
        repository.error = AppError.NoConnection

        assertEquals(AppError.NoConnection, useCase.createRandomBoard(4).exceptionOrNull())
    }

    @Test
    fun `createBoard adds every item twice with unique ids and all cards face down`() {
        val items = List(4) { item("item$it") }

        val result = useCase.createBoard(items)

        assertEquals(8, result.cards.size)
        assertEquals(8, result.cards.distinctBy { it.id }.size)
        assertEquals(items.associateWith { 2 }, result.cards.groupingBy { it.item }.eachCount())
        assertTrue(result.cards.all { it.state == CardState.FaceDown })
        assertEquals(0, result.moves)
    }

    @Test
    fun `createBoard shuffles the cards using the given random`() {
        val items = List(8) { item("item$it") }

        val first = useCase.createBoard(items, Random(1))

        assertEquals(first, useCase.createBoard(items, Random(1)))
        assertNotEquals(first, useCase.createBoard(items, Random(2)))
    }

    @Test
    fun `flipCard only turns the selected card face up`() {
        val result = useCase.flipCard(board, 0)

        assertEquals(listOf(0), result.faceUpCards.map { it.id })
        assertEquals(0, result.moves)
    }

    @Test
    fun `flipCard ignores an unknown card`() {
        assertEquals(board, useCase.flipCard(board, 99))
    }

    @Test
    fun `flipCard ignores a card that is already face up`() {
        val flipped = useCase.flipCard(board, 0)

        assertEquals(flipped, useCase.flipCard(flipped, 0))
    }

    @Test
    fun `flipCard ignores a third card`() {
        val flipped = flip(board, 0, 2)

        assertEquals(flipped, useCase.flipCard(flipped, 1))
    }

    @Test
    fun `flipCard ignores a matched card`() {
        val matched = playTurn(board, 0, 1)

        assertEquals(matched, useCase.flipCard(matched, 0))
    }

    @Test
    fun `resolveTurn does nothing with fewer than two cards face up`() {
        val flipped = useCase.flipCard(board, 0)

        assertEquals(board, useCase.resolveTurn(board))
        assertEquals(flipped, useCase.resolveTurn(flipped))
    }

    @Test
    fun `resolveTurn marks a pair as matched`() {
        val result = playTurn(board, 0, 1)

        assertEquals(
            listOf(CardState.Matched, CardState.Matched, CardState.FaceDown, CardState.FaceDown),
            result.cards.map { it.state },
        )
        assertEquals(1, result.moves)
    }

    @Test
    fun `resolveTurn turns different cards face down again`() {
        val result = playTurn(board, 0, 2)

        assertEquals(board.copy(moves = 1), result)
    }

    @Test
    fun `resolveTurn keeps previous matches when the next turn fails`() {
        val threePairs = board.copy(
            cards = board.cards + listOf(4, 5).map { MemoryCard(id = it, item = item("c")) },
        )
        val matched = playTurn(threePairs, 0, 1)

        assertEquals(matched.copy(moves = 2), playTurn(matched, 2, 4))
    }

    @Test
    fun `board is finished only when every pair is matched`() {
        val halfway = playTurn(board, 0, 1)
        val finished = playTurn(halfway, 2, 3)

        assertFalse(halfway.isFinished)
        assertTrue(finished.isFinished)
        assertEquals(2, finished.moves)
    }

    @Test
    fun `empty board is not finished`() {
        assertFalse(useCase.createBoard(emptyList()).isFinished)
    }

    private fun item(id: String) = MemoryItem(id = id, name = id, imageUrl = "https://example.com/$id.png")

    private fun flip(board: MemoryBoard, vararg cardIds: Int): MemoryBoard =
        cardIds.fold(board) { current, id -> useCase.flipCard(current, id) }

    private fun playTurn(board: MemoryBoard, first: Int, second: Int): MemoryBoard =
        useCase.resolveTurn(flip(board, first, second))
}
