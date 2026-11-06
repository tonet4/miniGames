package com.antonio.minigames.domain.menu.usecase

import com.antonio.minigames.domain.menu.model.MiniGame
import javax.inject.Inject

class GetGamesUseCase @Inject constructor() {

    operator fun invoke(): List<MiniGame> = MiniGame.entries
}
