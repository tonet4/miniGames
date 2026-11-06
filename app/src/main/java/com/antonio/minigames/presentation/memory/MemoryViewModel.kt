package com.antonio.minigames.presentation.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonio.minigames.presentation.memory.MemoryEffect.*
import com.antonio.minigames.presentation.memory.MemoryIntent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MemoryViewModel @Inject constructor(): ViewModel()  {

    private val _uiState = MutableStateFlow(MemoryUiState())
    val uiState = _uiState
        .onStart {
            loadMemory()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value
        )

    private val _uiEffect = MutableSharedFlow<MemoryEffect>()
    val effect = _uiEffect.asSharedFlow()


    fun handle(intent: MemoryIntent){
        when(intent){
            NavigateBack -> viewModelScope.launch { _uiEffect.emit(OnNavigateBack) }
        }
    }
    private fun loadMemory() { }
}