package com.antonio.minigames.domain.menu.usecase

import com.antonio.minigames.domain.menu.model.MiniGame
import javax.inject.Inject

class MenuUseCase @Inject constructor() {

    fun getGames(): List<MiniGame> = MiniGame.entries
}
