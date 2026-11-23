package com.antonio.minigames.presentation.main

import com.antonio.minigames.domain.settings.entity.ThemeMode
import com.antonio.minigames.domain.settings.usecase.SettingsUseCase
import com.antonio.minigames.testutil.FakeSettingsRepository
import com.antonio.minigames.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `state follows the saved theme mode`() = runTest {
        val repository = FakeSettingsRepository()
        val viewModel = MainViewModel(SettingsUseCase(repository))
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        assertEquals(MainUiState(isLoading = false, themeMode = null), viewModel.uiState.value)

        repository.themeMode.value = ThemeMode.DARK

        assertEquals(MainUiState(isLoading = false, themeMode = ThemeMode.DARK), viewModel.uiState.value)
    }
}
