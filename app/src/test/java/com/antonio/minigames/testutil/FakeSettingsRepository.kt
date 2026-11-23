package com.antonio.minigames.testutil

import com.antonio.minigames.domain.settings.SettingsRepository
import com.antonio.minigames.domain.settings.entity.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSettingsRepository : SettingsRepository {

    val themeMode = MutableStateFlow<ThemeMode?>(null)

    override fun observeThemeMode(): Flow<ThemeMode?> = themeMode

    override suspend fun setThemeMode(themeMode: ThemeMode): Result<Unit> {
        this.themeMode.value = themeMode
        return Result.success(Unit)
    }
}
