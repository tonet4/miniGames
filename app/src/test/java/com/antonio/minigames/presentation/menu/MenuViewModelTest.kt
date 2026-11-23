package com.antonio.minigames.presentation.menu

import com.antonio.minigames.domain.menu.entity.MiniGame
import com.antonio.minigames.domain.menu.usecase.MenuUseCase
import com.antonio.minigames.domain.settings.entity.ThemeMode
import com.antonio.minigames.domain.settings.usecase.SettingsUseCase
import com.antonio.minigames.testutil.FakeSettingsRepository
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

    private val settingsRepository = FakeSettingsRepository()

    private fun createViewModel() = MenuViewModel(MenuUseCase(), SettingsUseCase(settingsRepository))

    @Test
    fun `games are loaded when the state is collected`() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        assertEquals(MiniGame.entries.toList(), viewModel.uiState.value.games.map { it.game })
    }

    @Test
    fun `theme selected is saved and shown in the state`() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.handle(MenuIntent.ThemeSelected(ThemeMode.DARK))

        assertEquals(ThemeMode.DARK, settingsRepository.themeMode.value)
        assertEquals(ThemeMode.DARK, viewModel.uiState.value.themeMode)
    }

    @Test
    fun `game clicked opens that game`() = runTest {
        val viewModel = createViewModel()
        val effect = async(UnconfinedTestDispatcher(testScheduler)) { viewModel.effect.first() }

        viewModel.handle(MenuIntent.GameClicked(MiniGame.MEMORY))

        assertEquals(MenuEffect.OpenGame(MiniGame.MEMORY), effect.await())
    }
}
