package com.antonio.minigames.data.pokemon.datasource

import com.antonio.minigames.data.pokemon.remote.dto.PokemonCharacteristicsDto
import com.antonio.minigames.data.pokemon.remote.dto.PokemonPageDto

interface PokemonDataSource {
    suspend fun getPokemonPage(limit: Int, offset: Int): Result<PokemonPageDto>
    suspend fun getPokemonSpeciesPage(limit: Int, offset: Int): Result<PokemonPageDto>
    suspend fun getPokemonCharacteristics(idOrName: String): Result<PokemonCharacteristicsDto>
}
