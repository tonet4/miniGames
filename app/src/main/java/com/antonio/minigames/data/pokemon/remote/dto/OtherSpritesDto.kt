package com.antonio.minigames.data.pokemon.remote.dto

import com.google.gson.annotations.SerializedName

data class OtherSpritesDto(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtworkDto
)
