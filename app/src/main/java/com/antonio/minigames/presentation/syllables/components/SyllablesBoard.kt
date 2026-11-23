package com.antonio.minigames.presentation.syllables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.antonio.minigames.R
import com.antonio.minigames.domain.syllables.entity.SyllableRound
import com.antonio.minigames.domain.syllables.entity.SyllableTile
import com.antonio.minigames.domain.syllables.entity.Word
import com.antonio.minigames.presentation.shared.components.RetroButton
import com.antonio.minigames.presentation.syllables.toPhotoRes
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun SyllablesBoard(
    round: SyllableRound,
    wrongTileId: Int?,
    onTileTapped: (Int) -> Unit,
    onPhotoTapped: () -> Unit,
    onNext: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
    ) {
        WordPhotoCard(
            photoRes = round.word.toPhotoRes(),
            onClick = onPhotoTapped,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            round.word.syllables.forEachIndexed { index, syllable ->
                SyllableSlot(text = syllable.takeIf { index < round.placedCount })
            }
        }
        if (round.isFinished) {
            RetroButton(
                text = stringResource(R.string.next_word),
                onClick = onNext,
            )
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                round.tiles.forEach { tile ->
                    SyllableTileItem(
                        text = tile.text,
                        isPlaced = tile.isPlaced,
                        isWrong = tile.id == wrongTileId,
                        onClick = { onTileTapped(tile.id) },
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SyllablesBoardPreview() {
    MinigamesTheme {
        Surface {
            SyllablesBoard(
                round = SyllableRound(
                    word = Word.GATO,
                    tiles = listOf("TO", "GA", "MA").mapIndexed { id, text -> SyllableTile(id, text) },
                ),
                wrongTileId = null,
                onTileTapped = {},
                onPhotoTapped = {},
                onNext = {},
            )
        }
    }
}
