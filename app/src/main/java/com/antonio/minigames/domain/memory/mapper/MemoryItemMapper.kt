package com.antonio.minigames.domain.memory.mapper

import com.antonio.minigames.domain.memory.entity.MemoryItem
import com.antonio.minigames.domain.pokemon.entity.Pokemon

fun Pokemon.toMemoryItem(): MemoryItem? = imageUrl?.let { url ->
    MemoryItem(id = id.toString(), name = name, imageUrl = url)
}
