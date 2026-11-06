package com.antonio.minigames.presentation.menu

import com.antonio.minigames.domain.menu.model.MiniGame
import com.antonio.minigames.domain.menu.usecase.GetGamesUseCase
import com.antonio.minigames.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MenuViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel() = MenuViewModel(GetGamesUseCase())

    @Test
    fun `games are loaded when the state is collected`() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        assertEquals(listOf(MiniGame.MEMORY), viewModel.uiState.value.games.map { it.game })
    }

    @Test
    fun `game clicked opens that game`() = runTest {
        val viewModel = createViewModel()
        // SharedFlow has no replay: subscribe before the intent or the effect is lost.
        val effect = async(UnconfinedTestDispatcher(testScheduler)) { viewModel.effect.first() }

        viewModel.handle(MenuIntent.GameClicked(MiniGame.MEMORY))

        assertEquals(MenuEffect.OpenGame(MiniGame.MEMORY), effect.await())
    }
}
