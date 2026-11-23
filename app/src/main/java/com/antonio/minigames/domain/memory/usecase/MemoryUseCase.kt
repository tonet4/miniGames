package com.antonio.minigames.domain.memory.usecase

import com.antonio.minigames.domain.memory.entity.CardState
import com.antonio.minigames.domain.memory.entity.MemoryBoard
import com.antonio.minigames.domain.memory.entity.MemoryCard
import com.antonio.minigames.domain.memory.entity.MemoryItem
import com.antonio.minigames.domain.memory.mapper.toMemoryItem
import com.antonio.minigames.domain.pokemon.usecase.PokemonUseCase
import javax.inject.Inject
import kotlin.random.Random

class MemoryUseCase @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) {

    suspend fun createRandomBoard(pairs: Int): Result<MemoryBoard> =
        pokemonUseCase.getRandomPokemon(pairs).map { pokemon ->
            createBoard(pokemon.mapNotNull { it.toMemoryItem() })
        }

    fun createBoard(items: List<MemoryItem>, random: Random = Random.Default): MemoryBoard =
        MemoryBoard(
            cards = items
                .flatMap { listOf(it, it) }
                .shuffled(random)
                .mapIndexed { index, item -> MemoryCard(id = index, item = item) },
        )

    fun flipCard(board: MemoryBoard, cardId: Int): MemoryBoard {
        val card = board.cards.find { it.id == cardId } ?: return board
        if (card.state != CardState.FaceDown || board.faceUpCards.size >= 2) return board
        return board.copy(
            cards = board.cards.map {
                if (it.id == cardId) it.copy(state = CardState.FaceUp) else it
            },
        )
    }

    fun resolveTurn(board: MemoryBoard): MemoryBoard {
        val faceUp = board.faceUpCards
        if (faceUp.size < 2) return board
        val isMatch = faceUp.distinctBy { it.item.id }.size == 1
        val newState = if (isMatch) CardState.Matched else CardState.FaceDown
        return board.copy(
            cards = board.cards.map {
                if (it.state == CardState.FaceUp) it.copy(state = newState) else it
            },
            moves = board.moves + 1,
        )
    }
}
