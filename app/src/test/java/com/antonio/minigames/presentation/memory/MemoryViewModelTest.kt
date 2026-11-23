package com.antonio.minigames.presentation.memory

import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.memory.entity.CardState
import com.antonio.minigames.domain.memory.entity.MemoryBoard
import com.antonio.minigames.domain.memory.usecase.MemoryUseCase
import com.antonio.minigames.domain.pokemon.usecase.PokemonUseCase
import com.antonio.minigames.testutil.FakePokemonRepository
import com.antonio.minigames.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MemoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakePokemonRepository()

    @Test
    fun `board with nine pairs is loaded when the state is collected`() = runTest {
        val viewModel = startedViewModel()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(18, viewModel.board.cards.size)
        assertEquals(9, viewModel.board.cards.distinctBy { it.item.id }.size)
    }

    @Test
    fun `load error is shown and retry loads the board`() = runTest {
        repository.error = AppError.NoConnection
        val viewModel = startedViewModel()

        assertEquals(AppError.NoConnection, viewModel.uiState.value.error)
        assertNull(viewModel.uiState.value.board)
        assertFalse(viewModel.uiState.value.isLoading)

        repository.error = null
        viewModel.handle(MemoryIntent.RetryClicked)
        advanceUntilIdle()

        assertNull(viewModel.uiState.value.error)
        assertEquals(18, viewModel.board.cards.size)
    }

    @Test
    fun `different cards stay visible for one second and then turn face down`() = runTest {
        val viewModel = startedViewModel()
        val (first, second) = viewModel.board.cards.distinctBy { it.item.id }

        viewModel.tap(first.id, second.id)
        advanceTimeBy(1_000)
        assertEquals(2, viewModel.board.faceUpCards.size)

        runCurrent()
        assertTrue(viewModel.board.cards.all { it.state == CardState.FaceDown })
        assertEquals(1, viewModel.board.moves)
    }

    @Test
    fun `third card is ignored while two cards are visible`() = runTest {
        val viewModel = startedViewModel()
        val ids = viewModel.board.cards.distinctBy { it.item.id }.map { it.id }

        viewModel.tap(ids[0], ids[1], ids[2])
        runCurrent()

        assertEquals(setOf(ids[0], ids[1]), viewModel.board.faceUpCards.map { it.id }.toSet())
    }

    @Test
    fun `matching every pair finishes the game and restart creates a new board`() = runTest {
        val viewModel = startedViewModel()

        viewModel.board.cards.groupBy { it.item.id }.values.forEach { pair ->
            viewModel.tap(pair[0].id, pair[1].id)
            advanceUntilIdle()
        }
        assertTrue(viewModel.board.isFinished)

        viewModel.handle(MemoryIntent.RestartClicked)
        advanceUntilIdle()
        assertTrue(viewModel.board.cards.all { it.state == CardState.FaceDown })
        assertEquals(0, viewModel.board.moves)
    }

    @Test
    fun `navigate back emits the effect`() = runTest {
        val viewModel = createViewModel()
        val effect = async(UnconfinedTestDispatcher(testScheduler)) { viewModel.effect.first() }

        viewModel.handle(MemoryIntent.NavigateBack)

        assertEquals(MemoryEffect.OnNavigateBack, effect.await())
    }

    private val MemoryViewModel.board: MemoryBoard
        get() = checkNotNull(uiState.value.board)

    private fun MemoryViewModel.tap(vararg ids: Int) =
        ids.forEach { handle(MemoryIntent.CardTapped(it)) }

    private fun createViewModel() = MemoryViewModel(MemoryUseCase(PokemonUseCase(repository)))

    private fun TestScope.startedViewModel(): MemoryViewModel {
        val viewModel = createViewModel()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }
        advanceUntilIdle()
        return viewModel
    }
}
