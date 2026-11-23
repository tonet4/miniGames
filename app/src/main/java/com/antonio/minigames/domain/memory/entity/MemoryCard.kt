package com.antonio.minigames.domain.memory.entity

data class MemoryCard(
    val id: Int,
    val item: MemoryItem,
    val state: CardState = CardState.FaceDown,
)
