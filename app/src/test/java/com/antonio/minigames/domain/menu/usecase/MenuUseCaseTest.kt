package com.antonio.minigames.domain.menu.usecase

import com.antonio.minigames.domain.menu.model.MiniGame
import org.junit.Assert.assertEquals
import org.junit.Test

class MenuUseCaseTest {

    @Test
    fun `returns every mini game`() {
        assertEquals(MiniGame.entries, MenuUseCase().getGames())
    }
}
