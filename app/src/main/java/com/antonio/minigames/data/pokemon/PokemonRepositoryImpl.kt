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
        dataSource.getPokemonPage(limit = 1, offset = 0).map { it.count }

    override suspend fun getPokemonSpeciesCount(): Result<Int> =
        dataSource.getPokemonSpeciesPage(limit = 1, offset = 0).map { it.count }

    override suspend fun getPokemonNames(limit: Int, offset: Int): Result<List<String>> =
        dataSource.getPokemonPage(limit = limit, offset = offset)
            .map { page -> page.results.map { it.name } }

    override suspend fun getPokemon(idOrName: String): Result<Pokemon> =
        dataSource.getPokemonCharacteristics(idOrName).map { it.toDomain() }
}
