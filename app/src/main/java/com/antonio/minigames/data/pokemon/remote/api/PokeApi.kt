package com.antonio.minigames.data.pokemon.remote.api

import com.antonio.minigames.data.pokemon.remote.dto.PokemonCharacteristicsDto
import com.antonio.minigames.data.pokemon.remote.dto.PokemonPageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonPage(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<PokemonPageDto>

    @GET("pokemon-species")
    suspend fun getPokemonSpeciesPage(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<PokemonPageDto>

    @GET("pokemon/{idOrName}")
    suspend fun getPokemonCharacteristics(
        @Path("idOrName") idOrName: String,
    ): Response<PokemonCharacteristicsDto>
}
