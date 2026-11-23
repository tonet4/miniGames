package com.antonio.minigames.presentation.syllables

import com.antonio.minigames.domain.syllables.entity.SyllableRound
import com.antonio.minigames.domain.syllables.usecase.SyllablesUseCase
import com.antonio.minigames.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SyllablesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `wrong tile is marked and then cleared`() = runTest {
        val viewModel = startedViewModel()
        val wrong = viewModel.round.tiles.first { it.text != viewModel.round.nextSyllable }

        viewModel.handle(SyllablesIntent.TileTapped(wrong.id))
        assertEquals(wrong.id, viewModel.uiState.value.wrongTileId)
        assertEquals(0, viewModel.round.placedCount)

        advanceUntilIdle()
        assertNull(viewModel.uiState.value.wrongTileId)
    }

    @Test
    fun `next opens another word only when the word is finished`() = runTest {
        val viewModel = startedViewModel()
        val word = viewModel.round.word

        viewModel.handle(SyllablesIntent.NextClicked)
        assertEquals(word, viewModel.round.word)

        word.syllables.forEach { viewModel.tap(it) }
        assertTrue(viewModel.round.isFinished)

        viewModel.handle(SyllablesIntent.NextClicked)
        assertNotEquals(word, viewModel.round.word)
    }

    @Test
    fun `tapping the photo speaks the word`() = runTest {
        val viewModel = startedViewModel()
        val effect = async(UnconfinedTestDispatcher(testScheduler)) { viewModel.effect.first() }

        viewModel.handle(SyllablesIntent.PhotoTapped)

        assertEquals(SyllablesEffect.Speak(viewModel.round.word.name), effect.await())
    }

    private val SyllablesViewModel.round: SyllableRound
        get() = checkNotNull(uiState.value.round)

    private fun SyllablesViewModel.tap(text: String) =
        handle(SyllablesIntent.TileTapped(round.tiles.first { it.text == text }.id))

    private fun TestScope.startedViewModel(): SyllablesViewModel {
        val viewModel = SyllablesViewModel(SyllablesUseCase())
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }
        advanceUntilIdle()
        return viewModel
    }
}
