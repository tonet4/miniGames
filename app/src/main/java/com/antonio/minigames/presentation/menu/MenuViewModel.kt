package com.antonio.minigames.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonio.minigames.domain.menu.usecase.MenuUseCase
import com.antonio.minigames.domain.settings.usecase.SettingsUseCase
import com.antonio.minigames.presentation.menu.MenuIntent.GameClicked
import com.antonio.minigames.presentation.menu.MenuIntent.ThemeSelected
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuUseCase: MenuUseCase,
    private val settingsUseCase: SettingsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState
        .onStart {
            loadMenu()
            observeThemeMode()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value,
        )

    private val _uiEffect = MutableSharedFlow<MenuEffect>()
    val effect = _uiEffect.asSharedFlow()

    fun handle(intent: MenuIntent) {
        when (intent) {
            is GameClicked -> viewModelScope.launch {
                _uiEffect.emit(MenuEffect.OpenGame(intent.game))
            }
            is ThemeSelected -> viewModelScope.launch {
                settingsUseCase.setThemeMode(intent.themeMode)
            }
        }
    }

    private fun observeThemeMode() {
        viewModelScope.launch {
            settingsUseCase.observeThemeMode().collect { themeMode ->
                _uiState.update { it.copy(themeMode = themeMode) }
            }
        }
    }

    private fun loadMenu() {
        _uiState.update { it.copy(games = menuUseCase.getGames().map { game -> game.toGameItem() }) }
    }
}
