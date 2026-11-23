package com.antonio.minigames.presentation.memory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antonio.minigames.R
import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.domain.memory.entity.CardState
import com.antonio.minigames.domain.memory.entity.MemoryBoard
import com.antonio.minigames.domain.memory.entity.MemoryCard
import com.antonio.minigames.domain.memory.entity.MemoryItem
import com.antonio.minigames.presentation.memory.MemoryEffect.OnNavigateBack
import com.antonio.minigames.presentation.memory.MemoryIntent.CardTapped
import com.antonio.minigames.presentation.memory.MemoryIntent.NavigateBack
import com.antonio.minigames.presentation.memory.MemoryIntent.RestartClicked
import com.antonio.minigames.presentation.memory.MemoryIntent.RetryClicked
import com.antonio.minigames.presentation.memory.components.MemoryError
import com.antonio.minigames.presentation.memory.components.MemoryGame
import com.antonio.minigames.presentation.shared.components.CustomTopBar
import com.antonio.minigames.presentation.shared.components.retroBackground
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun MemoryScreen(
    viewModel: MemoryViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnNavigateBack -> onNavigateBack()
            }
        }
    }

    MemoryScreenScaffold(
        isLoading = uiState.isLoading,
        board = uiState.board,
        error = uiState.error,
        onIntent = viewModel::handle
    )
}

@Composable
private fun MemoryScreenScaffold(
    isLoading: Boolean,
    board: MemoryBoard?,
    error: AppError?,
    onIntent: (MemoryIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.game_memory),
                onNavigateLeftIcon = { onIntent(NavigateBack) }
            )
        },
    ) { innerPadding ->
        MemoryContent(
            isLoading = isLoading,
            board = board,
            error = error,
            onIntent = onIntent,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun MemoryContent(
    isLoading: Boolean,
    board: MemoryBoard?,
    error: AppError?,
    onIntent: (MemoryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .retroBackground()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> MemoryError(
                error = error,
                onRetry = { onIntent(RetryClicked) },
            )
            board != null -> MemoryGame(
                board = board,
                onCardTapped = { id -> onIntent(CardTapped(id)) },
                onPlayAgain = { onIntent(RestartClicked) },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MemoryScreenPreview() {
    MinigamesTheme {
        MemoryScreenScaffold(
            isLoading = false,
            board = MemoryBoard(
                cards = List(18) { index ->
                    MemoryCard(
                        id = index,
                        item = MemoryItem(id = "${index / 2}", name = "", imageUrl = ""),
                        state = if (index < 2) CardState.Matched else CardState.FaceDown,
                    )
                },
            ),
            error = null,
            onIntent = {},
        )
    }
}
