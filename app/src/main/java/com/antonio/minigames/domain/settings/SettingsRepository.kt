package com.antonio.minigames.domain.settings

import com.antonio.minigames.domain.settings.entity.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun observeThemeMode(): Flow<ThemeMode?>

    suspend fun setThemeMode(themeMode: ThemeMode): Result<Unit>
}
