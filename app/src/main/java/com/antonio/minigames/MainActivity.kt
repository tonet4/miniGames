package com.antonio.minigames

import android.app.UiModeManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antonio.minigames.domain.settings.entity.ThemeMode
import com.antonio.minigames.presentation.main.MainViewModel
import com.antonio.minigames.presentation.navigation.MiniGamesNavHost
import com.antonio.minigames.presentation.shared.isDark
import com.antonio.minigames.ui.theme.MinigamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.isLoading }
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val isDarkTheme = uiState.themeMode.isDark()

            LaunchedEffect(isDarkTheme) { applySystemBars(isDarkTheme) }
            LaunchedEffect(uiState.themeMode) { uiState.themeMode?.let { applyNightMode(it) } }

            MinigamesTheme(darkTheme = isDarkTheme) {
                MiniGamesNavHost()
            }
        }
    }

    private fun applySystemBars(isDarkTheme: Boolean) {
        val style = SystemBarStyle.auto(
            lightScrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT,
            detectDarkMode = { isDarkTheme },
        )
        enableEdgeToEdge(statusBarStyle = style, navigationBarStyle = style)
    }

    private fun applyNightMode(themeMode: ThemeMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val nightMode = when (themeMode) {
                ThemeMode.LIGHT -> UiModeManager.MODE_NIGHT_NO
                ThemeMode.DARK -> UiModeManager.MODE_NIGHT_YES
            }
            getSystemService(UiModeManager::class.java).setApplicationNightMode(nightMode)
        }
    }
}
