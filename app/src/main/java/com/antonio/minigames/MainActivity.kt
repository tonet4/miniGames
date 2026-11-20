package com.antonio.minigames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.antonio.minigames.presentation.navigation.MiniGamesNavHost
import com.antonio.minigames.ui.theme.MinigamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinigamesTheme {
                MiniGamesNavHost()
            }
        }
    }
}
