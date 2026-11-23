package com.antonio.minigames.presentation.memory

import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.memory.entity.MemoryBoard

data class MemoryUiState(
    val isLoading: Boolean = true,
    val board: MemoryBoard? = null,
    val error: AppError? = null,
)

sealed interface MemoryIntent {
    data class CardTapped(val id: Int) : MemoryIntent
    data object RestartClicked : MemoryIntent
    data object RetryClicked : MemoryIntent
    data object NavigateBack : MemoryIntent
}

sealed interface MemoryEffect {
    data object OnNavigateBack : MemoryEffect
}
