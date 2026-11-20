package com.antonio.minigames.data.pokemon.remote.dto

data class PokemonCharacteristicsDto(
    val id: Int,
    val name: String,
    val sprites: SpritesDto,
    val types: List<PokemonTypeSlotDto>
)
