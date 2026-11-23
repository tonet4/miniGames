package com.antonio.minigames.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.settings.SettingsRepository
import com.antonio.minigames.domain.settings.entity.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val THEME_MODE = stringPreferencesKey("theme_mode")

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    override fun observeThemeMode(): Flow<ThemeMode?> = dataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { preferences ->
            ThemeMode.entries.find { it.name == preferences[THEME_MODE] }
        }
        .distinctUntilChanged()

    override suspend fun setThemeMode(themeMode: ThemeMode): Result<Unit> =
        try {
            dataStore.edit { it[THEME_MODE] = themeMode.name }
            Result.success(Unit)
        } catch (error: IOException) {
            Result.failure(AppError.Unexpected(error))
        }
}
