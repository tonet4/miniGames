package com.antonio.minigames.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antonio.minigames.domain.menu.model.MiniGame
import com.antonio.minigames.presentation.memory.MemoryScreen
import com.antonio.minigames.presentation.menu.MenuScreen

@Composable
fun MiniGamesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MenuScreen,
        modifier = modifier,
    ) {
        composable<MenuScreen> {
            MenuScreen(onOpenGame = { game -> navController.navigate(game.toDestination()) })
        }
        composable<MemoryScreen> {
            MemoryScreen (onNavigateBack = { navController.popBackStack() })
        }
    }
}

private fun MiniGame.toDestination(): Any = when (this) {
    MiniGame.MEMORY -> MemoryScreen
}
