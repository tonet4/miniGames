package com.antonio.minigames.data.pokemon.mapper

import com.antonio.minigames.data.pokemon.remote.dto.PokemonCharacteristicsDto
import com.antonio.minigames.domain.pokemon.model.Pokemon

fun PokemonCharacteristicsDto.toDomain(): Pokemon = Pokemon(
    id = id,
    name = name,
    imageUrl = sprites.other.officialArtwork.frontDefault,
    types = types.map { it.type.name },
)
