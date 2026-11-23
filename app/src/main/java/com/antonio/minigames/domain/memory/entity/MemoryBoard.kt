package com.antonio.minigames.domain.memory.entity

data class MemoryBoard(
    val cards: List<MemoryCard>,
    val moves: Int = 0,
) {
    val faceUpCards: List<MemoryCard>
        get() = cards.filter { it.state == CardState.FaceUp }

    val isFinished: Boolean
        get() = cards.isNotEmpty() && cards.all { it.state == CardState.Matched }
}
