package com.antonio.minigames.di

import com.antonio.minigames.data.pokemon.PokemonRepositoryImpl
import com.antonio.minigames.data.pokemon.datasource.PokemonDataSource
import com.antonio.minigames.data.pokemon.remote.PokemonDataSourceImpl
import com.antonio.minigames.domain.pokemon.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PokemonModule {

    @Binds
    abstract fun bindPokemonDataSource(
        impl: PokemonDataSourceImpl,
    ): PokemonDataSource

    @Binds
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl,
    ): PokemonRepository
}
