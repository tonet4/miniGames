package com.antonio.minigames.domain.pokemon.usecase

import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.pokemon.PokemonRepository
import com.antonio.minigames.domain.pokemon.entity.Pokemon
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class PokemonUseCase @Inject constructor(
    private val repository: PokemonRepository,
) {

    suspend fun getRandomPokemon(amount: Int): Result<List<Pokemon>> =
        try {
            val speciesCount = repository.getPokemonSpeciesCount().getOrThrow()
            val names = repository.getPokemonNames(limit = speciesCount, offset = 0).getOrThrow()

            val selected = coroutineScope {
                names.shuffled()
                    .take(amount)
                    .map { name -> async { repository.getPokemon(name).getOrThrow() } }
                    .awaitAll()
            }
            check(selected.size == amount) { "Not enough Pokémon: ${selected.size} < $amount" }
            check(selected.all { it.imageUrl != null }) { "Some Pokémon have no image" }
            Result.success(selected)
        } catch (error: CancellationException) {
            throw error
        } catch (error: AppError) {
            Result.failure(error)
        } catch (error: Exception) {
            Result.failure(AppError.Unexpected(error))
        }
}
