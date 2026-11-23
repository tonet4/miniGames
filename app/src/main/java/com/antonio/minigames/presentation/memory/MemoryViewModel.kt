package com.antonio.minigames.presentation.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.memory.usecase.MemoryUseCase
import com.antonio.minigames.presentation.memory.MemoryEffect.OnNavigateBack
import com.antonio.minigames.presentation.memory.MemoryIntent.CardTapped
import com.antonio.minigames.presentation.memory.MemoryIntent.NavigateBack
import com.antonio.minigames.presentation.memory.MemoryIntent.RestartClicked
import com.antonio.minigames.presentation.memory.MemoryIntent.RetryClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val PAIRS = 9
private const val TURN_DELAY_MILLIS = 1_000L

@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val memoryUseCase: MemoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemoryUiState())
    val uiState: StateFlow<MemoryUiState> = _uiState
        .onStart { loadMemory() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value,
        )

    private val _uiEffect = MutableSharedFlow<MemoryEffect>()
    val effect = _uiEffect.asSharedFlow()

    private var loadJob: Job? = null

    fun handle(intent: MemoryIntent) {
        when (intent) {
            is CardTapped -> onCardTapped(intent.id)
            RestartClicked, RetryClicked -> loadMemory()
            NavigateBack -> viewModelScope.launch { _uiEffect.emit(OnNavigateBack) }
        }
    }

    private fun loadMemory() {
        loadJob?.cancel()
        _uiState.update { MemoryUiState() }
        loadJob = viewModelScope.launch {
            memoryUseCase.createRandomBoard(PAIRS).fold(
                onSuccess = { board ->
                    _uiState.update { it.copy(isLoading = false, board = board) }
                },
                onFailure = { error ->
                    val appError = error as? AppError ?: AppError.Unexpected(error)
                    _uiState.update { it.copy(isLoading = false, error = appError) }
                },
            )
        }
    }

    private fun onCardTapped(cardId: Int) {
        val board = _uiState.value.board ?: return
        val flipped = memoryUseCase.flipCard(board, cardId)
        if (flipped == board) return
        _uiState.update { it.copy(board = flipped) }
        if (flipped.faceUpCards.size == 2) resolveTurnAfterDelay()
    }

    private fun resolveTurnAfterDelay() {
        viewModelScope.launch {
            delay(TURN_DELAY_MILLIS)
            _uiState.update { state ->
                state.copy(board = state.board?.let(memoryUseCase::resolveTurn))
            }
        }
    }
}
