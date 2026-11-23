package com.antonio.minigames.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonio.minigames.domain.settings.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsUseCase: SettingsUseCase,
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = settingsUseCase.observeThemeMode()
        .map { themeMode -> MainUiState(isLoading = false, themeMode = themeMode) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = MainUiState(),
        )
}
