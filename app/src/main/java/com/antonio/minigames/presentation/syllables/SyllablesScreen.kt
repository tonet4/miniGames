package com.antonio.minigames.presentation.syllables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antonio.minigames.R
import com.antonio.minigames.domain.syllables.entity.SyllableRound
import com.antonio.minigames.domain.syllables.entity.SyllableTile
import com.antonio.minigames.domain.syllables.entity.Word
import com.antonio.minigames.presentation.shared.components.CustomTopBar
import com.antonio.minigames.presentation.shared.components.retroBackground
import com.antonio.minigames.presentation.syllables.SyllablesEffect.OnNavigateBack
import com.antonio.minigames.presentation.syllables.SyllablesEffect.Speak
import com.antonio.minigames.presentation.syllables.SyllablesIntent.NavigateBack
import com.antonio.minigames.presentation.syllables.SyllablesIntent.NextClicked
import com.antonio.minigames.presentation.syllables.SyllablesIntent.PhotoTapped
import com.antonio.minigames.presentation.syllables.SyllablesIntent.TileTapped
import com.antonio.minigames.presentation.syllables.components.SyllablesBoard
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun SyllablesScreen(
    viewModel: SyllablesViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val speaker = rememberSpeaker()

    LaunchedEffect(viewModel.effect, speaker) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is Speak -> speaker.speak(effect.text)
                OnNavigateBack -> onNavigateBack()
            }
        }
    }

    SyllablesScreenScaffold(
        round = uiState.round,
        wrongTileId = uiState.wrongTileId,
        onIntent = viewModel::handle,
    )
}

@Composable
private fun SyllablesScreenScaffold(
    round: SyllableRound?,
    wrongTileId: Int?,
    onIntent: (SyllablesIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.game_syllables),
                onNavigateLeftIcon = { onIntent(NavigateBack) },
            )
        },
    ) { innerPadding ->
        SyllablesContent(
            round = round,
            wrongTileId = wrongTileId,
            onIntent = onIntent,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun SyllablesContent(
    round: SyllableRound?,
    wrongTileId: Int?,
    onIntent: (SyllablesIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .retroBackground(),
    ) {
        if (round != null) {
            SyllablesBoard(
                round = round,
                wrongTileId = wrongTileId,
                onTileTapped = { id -> onIntent(TileTapped(id)) },
                onPhotoTapped = { onIntent(PhotoTapped) },
                onNext = { onIntent(NextClicked) },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SyllablesScreenPreview() {
    MinigamesTheme {
        SyllablesScreenScaffold(
            round = SyllableRound(
                word = Word.GATO,
                tiles = listOf("TO", "GA", "MA").mapIndexed { id, text -> SyllableTile(id, text) },
            ),
            wrongTileId = null,
            onIntent = {},
        )
    }
}
