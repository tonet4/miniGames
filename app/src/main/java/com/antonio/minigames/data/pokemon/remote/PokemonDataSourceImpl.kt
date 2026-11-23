package com.antonio.minigames.data.pokemon.remote

import com.antonio.minigames.data.error.safeApiCall
import com.antonio.minigames.data.pokemon.datasource.PokemonDataSource
import com.antonio.minigames.data.pokemon.remote.api.PokeApi
import com.antonio.minigames.data.pokemon.remote.dto.PokemonCharacteristicsDto
import com.antonio.minigames.data.pokemon.remote.dto.PokemonPageDto
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokemonApi: PokeApi,
) : PokemonDataSource {

    override suspend fun getPokemonPage(limit: Int, offset: Int): Result<PokemonPageDto> =
        safeApiCall { pokemonApi.getPokemonPage(limit = limit, offset = offset) }

    override suspend fun getPokemonSpeciesPage(limit: Int, offset: Int): Result<PokemonPageDto> =
        safeApiCall { pokemonApi.getPokemonSpeciesPage(limit = limit, offset = offset) }

    override suspend fun getPokemonCharacteristics(idOrName: String): Result<PokemonCharacteristicsDto> =
        safeApiCall { pokemonApi.getPokemonCharacteristics(idOrName) }
}
