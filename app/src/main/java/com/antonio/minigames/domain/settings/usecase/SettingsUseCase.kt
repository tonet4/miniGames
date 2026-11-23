package com.antonio.minigames.domain.settings.usecase

import com.antonio.minigames.domain.settings.SettingsRepository
import com.antonio.minigames.domain.settings.entity.ThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    fun observeThemeMode(): Flow<ThemeMode?> = repository.observeThemeMode()

    suspend fun setThemeMode(themeMode: ThemeMode): Result<Unit> =
        repository.setThemeMode(themeMode)
}
