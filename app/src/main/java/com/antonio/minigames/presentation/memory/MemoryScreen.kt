package com.antonio.minigames.presentation.memory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antonio.minigames.R
import com.antonio.minigames.presentation.memory.MemoryEffect.OnNavigateBack
import com.antonio.minigames.presentation.shared.components.CustomTopBar
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun MemoryScreen(
    viewModel: MemoryViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect{
            effect ->
            when(effect){
                OnNavigateBack -> onNavigateBack()
            }
        }
    }


    MemoryScreenScaffold(
        uiState = uiState,
        onIntent = viewModel::handle
    )
}

@Composable
private fun MemoryScreenScaffold(
    uiState: MemoryUiState,
    onIntent: (MemoryIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.game_memory),
                onNavigateLeftIcon = { onIntent(MemoryIntent.NavigateBack)}
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.coming_soon),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoryScreenPreview() {
    MinigamesTheme {
        MemoryScreenScaffold(
            uiState = MemoryUiState(),
            onIntent = {},
        )
    }
}
