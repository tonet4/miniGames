package com.antonio.minigames.domain.pokemon

import com.antonio.minigames.domain.pokemon.entity.Pokemon

interface PokemonRepository {

    suspend fun getPokemonCount(): Result<Int>

    suspend fun getPokemonSpeciesCount(): Result<Int>

    suspend fun getPokemonNames(limit: Int, offset: Int): Result<List<String>>

    suspend fun getPokemon(idOrName: String): Result<Pokemon>
}
