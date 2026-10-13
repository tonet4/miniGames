package com.antonio.minigames.core.mvi

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MviViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private data class CounterState(val count: Int = 0) : UiState

    private sealed interface CounterIntent : UiIntent {
        data object Increment : CounterIntent
        data object Finish : CounterIntent
    }

    private sealed interface CounterEffect : UiEffect {
        data class Finished(val total: Int) : CounterEffect
    }

    private class CounterViewModel :
        MviViewModel<CounterState, CounterIntent, CounterEffect>(CounterState()) {

        override fun onIntent(intent: CounterIntent) {
            when (intent) {
                CounterIntent.Increment -> setState { copy(count = count + 1) }
                CounterIntent.Finish -> sendEffect(CounterEffect.Finished(currentState.count))
            }
        }
    }

    @Test
    fun `initial state is exposed`() {
        val viewModel = CounterViewModel()

        assertEquals(CounterState(count = 0), viewModel.state.value)
    }

    @Test
    fun `intent updates state`() {
        val viewModel = CounterViewModel()

        viewModel.onIntent(CounterIntent.Increment)
        viewModel.onIntent(CounterIntent.Increment)

        assertEquals(2, viewModel.state.value.count)
    }

    @Test
    fun `intent sends effect with current state`() = runTest {
        val viewModel = CounterViewModel()

        viewModel.onIntent(CounterIntent.Increment)
        viewModel.onIntent(CounterIntent.Finish)

        assertEquals(CounterEffect.Finished(total = 1), viewModel.effects.first())
    }
}
