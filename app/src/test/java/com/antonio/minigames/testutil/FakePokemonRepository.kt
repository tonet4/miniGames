package com.antonio.minigames.testutil

import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.pokemon.PokemonRepository
import com.antonio.minigames.domain.pokemon.entity.Pokemon

class FakePokemonRepository : PokemonRepository {
    var error: AppError? = null

    override suspend fun getPokemonCount(): Result<Int> = respond(10)

    override suspend fun getPokemonSpeciesCount(): Result<Int> = respond(10)

    override suspend fun getPokemonNames(limit: Int, offset: Int): Result<List<String>> =
        respond(List(10) { it.toString() })

    override suspend fun getPokemon(idOrName: String): Result<Pokemon> = respond(
        Pokemon(
            id = idOrName.toInt(),
            name = idOrName,
            imageUrl = "https://example.com/$idOrName.png",
            types = emptyList(),
        ),
    )

    private fun <T> respond(value: T): Result<T> =
        error?.let { Result.failure(it) } ?: Result.success(value)
}
