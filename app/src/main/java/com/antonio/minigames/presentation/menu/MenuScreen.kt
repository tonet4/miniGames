package com.antonio.minigames.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antonio.minigames.R
import com.antonio.minigames.domain.menu.entity.MiniGame
import com.antonio.minigames.presentation.menu.components.GameCard
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    onOpenGame: (MiniGame) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MenuEffect.OpenGame -> onOpenGame(effect.game)
            }
        }
    }

    MenuScreenScaffold(
        games = uiState.games,
        onIntent = viewModel::handle,
    )
}

@Composable
private fun MenuScreenScaffold(
    games: List<GameItem>,
    onIntent: (MenuIntent) -> Unit,
) {
    Scaffold { innerPadding ->
        MenuContent(
            games = games,
            onIntent = onIntent,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun MenuContent(
    games: List<GameItem>,
    onIntent: (MenuIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = stringResource(R.string.menu_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            items(games, key = { it.game }) { item ->
                GameCard(
                    item = item,
                    onClick = { onIntent(MenuIntent.GameClicked(item.game)) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuScreenPreview() {
    MinigamesTheme {
        MenuScreenScaffold(
            games = listOf(MiniGame.MEMORY.toGameItem()),
            onIntent = {},
        )
    }
}
