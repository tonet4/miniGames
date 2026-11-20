package com.antonio.minigames.data.pokemon.remote

import com.antonio.minigames.data.pokemon.datasource.PokemonDataSource
import com.antonio.minigames.data.pokemon.remote.api.PokeApi
import com.antonio.minigames.data.pokemon.remote.dto.PokemonCharacteristicsDto
import com.antonio.minigames.data.pokemon.remote.dto.PokemonPageDto
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokemonApi: PokeApi,
) : PokemonDataSource {

    override suspend fun getPokemonPage(limit: Int, offset: Int): PokemonPageDto? =
        safeCall { pokemonApi.getPokemonPage(limit = limit, offset = offset).body() }

    override suspend fun getPokemonSpeciesPage(limit: Int, offset: Int): PokemonPageDto? =
        safeCall { pokemonApi.getPokemonSpeciesPage(limit = limit, offset = offset).body() }

    override suspend fun getPokemonCharacteristics(idOrName: String): PokemonCharacteristicsDto? =
        safeCall { pokemonApi.getPokemonCharacteristics(idOrName).body() }

    private suspend fun <T> safeCall(block: suspend () -> T?): T? =
        try {
            block()
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            null
        }
}
