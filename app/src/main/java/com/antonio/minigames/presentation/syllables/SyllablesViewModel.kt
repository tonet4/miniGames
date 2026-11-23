package com.antonio.minigames.presentation.syllables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonio.minigames.domain.syllables.entity.Word
import com.antonio.minigames.domain.syllables.usecase.SyllablesUseCase
import com.antonio.minigames.presentation.syllables.SyllablesEffect.OnNavigateBack
import com.antonio.minigames.presentation.syllables.SyllablesEffect.Speak
import com.antonio.minigames.presentation.syllables.SyllablesIntent.NavigateBack
import com.antonio.minigames.presentation.syllables.SyllablesIntent.NextClicked
import com.antonio.minigames.presentation.syllables.SyllablesIntent.PhotoTapped
import com.antonio.minigames.presentation.syllables.SyllablesIntent.TileTapped
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

private const val WRONG_TILE_MILLIS = 900L

@HiltViewModel
class SyllablesViewModel @Inject constructor(
    private val syllablesUseCase: SyllablesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SyllablesUiState())
    val uiState: StateFlow<SyllablesUiState> = _uiState
        .onStart { loadNextWord() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value,
        )

    private val _uiEffect = MutableSharedFlow<SyllablesEffect>()
    val effect = _uiEffect.asSharedFlow()

    private var pendingWords: List<Word> = emptyList()
    private var wrongTileJob: Job? = null

    fun handle(intent: SyllablesIntent) {
        when (intent) {
            is TileTapped -> onTileTapped(intent.id)
            PhotoTapped -> _uiState.value.round?.let { sendEffect(Speak(it.word.name)) }
            NextClicked -> if (_uiState.value.round?.isFinished == true) loadNextWord()
            NavigateBack -> sendEffect(OnNavigateBack)
        }
    }

    private fun loadNextWord() {
        wrongTileJob?.cancel()
        val words = pendingWords.ifEmpty { syllablesUseCase.getShuffledWords() }
        pendingWords = words.drop(1)
        _uiState.update { SyllablesUiState(round = syllablesUseCase.createRound(words.first())) }
    }

    private fun onTileTapped(tileId: Int) {
        val round = _uiState.value.round ?: return
        val tile = round.tiles.find { it.id == tileId } ?: return
        if (tile.isPlaced || round.isFinished) return

        val updated = syllablesUseCase.placeTile(round, tileId)
        val isWrong = updated == round
        wrongTileJob?.cancel()
        _uiState.update { it.copy(round = updated, wrongTileId = tileId.takeIf { isWrong }) }
        sendEffect(Speak(if (updated.isFinished) updated.word.name else tile.text))
        if (isWrong) clearWrongTileAfterDelay()
    }

    private fun clearWrongTileAfterDelay() {
        wrongTileJob = viewModelScope.launch {
            delay(WRONG_TILE_MILLIS)
            _uiState.update { it.copy(wrongTileId = null) }
        }
    }

    private fun sendEffect(effect: SyllablesEffect) {
        viewModelScope.launch { _uiEffect.emit(effect) }
    }
}
