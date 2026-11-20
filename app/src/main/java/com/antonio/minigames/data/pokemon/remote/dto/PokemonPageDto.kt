package com.antonio.minigames.data.pokemon.remote.dto

data class PokemonPageDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>,
)
