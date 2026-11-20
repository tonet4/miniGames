package com.antonio.minigames.presentation.memory

data class MemoryUiState(
    val isLoading: Boolean = true
)

sealed interface MemoryIntent {
    data object NavigateBack : MemoryIntent
}

sealed interface MemoryEffect {
    data object OnNavigateBack : MemoryEffect
}