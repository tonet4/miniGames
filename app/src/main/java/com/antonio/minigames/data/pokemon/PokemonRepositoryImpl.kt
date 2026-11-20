package com.antonio.minigames.data.pokemon

import com.antonio.minigames.data.pokemon.datasource.PokemonDataSource
import com.antonio.minigames.data.pokemon.mapper.toDomain
import com.antonio.minigames.domain.pokemon.PokemonRepository
import com.antonio.minigames.domain.pokemon.model.Pokemon
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val dataSource: PokemonDataSource,
) : PokemonRepository {

    override suspend fun getPokemonCount(): Result<Int> =
        dataSource.getPokemonPage(limit = 1, offset = 0)
            ?.let { Result.success(it.count) }
            ?: failure("Could not load Pokémon count")

    override suspend fun getPokemonSpeciesCount(): Result<Int> =
        dataSource.getPokemonSpeciesPage(limit = 1, offset = 0)
            ?.let { Result.success(it.count) }
            ?: failure("Could not load Pokémon species count")

    override suspend fun getPokemonNames(limit: Int, offset: Int): Result<List<String>> =
        dataSource.getPokemonPage(limit = limit, offset = offset)
            ?.let { page -> Result.success(page.results.map { it.name }) }
            ?: failure("Could not load Pokémon page (limit=$limit, offset=$offset)")

    override suspend fun getPokemon(idOrName: String): Result<Pokemon> =
        dataSource.getPokemonCharacteristics(idOrName)
            ?.let { Result.success(it.toDomain()) }
            ?: failure("Could not load Pokémon $idOrName")

    private fun <T> failure(message: String): Result<T> =
        Result.failure(IllegalStateException(message))
}
