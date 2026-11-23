package com.antonio.minigames.presentation.memory.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antonio.minigames.domain.memory.entity.CardState
import com.antonio.minigames.domain.memory.entity.MemoryCard
import com.antonio.minigames.domain.memory.entity.MemoryItem
import com.antonio.minigames.ui.theme.MinigamesTheme

private const val PORTRAIT_COLUMNS = 3
private const val LANDSCAPE_COLUMNS = 6

@Composable
fun MemoryGrid(
    cards: List<MemoryCard>,
    onCardTapped: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val columns = if (maxWidth > maxHeight) LANDSCAPE_COLUMNS else PORTRAIT_COLUMNS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            cards.chunked(columns).forEach { rowCards ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    rowCards.forEach { card ->
                        MemoryCardItem(
                            card = card,
                            onClick = { onCardTapped(card.id) },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoryGridPreview() {
    MinigamesTheme {
        MemoryGrid(
            cards = List(18) { index ->
                MemoryCard(
                    id = index,
                    item = MemoryItem(id = "${index / 2}", name = "", imageUrl = ""),
                    state = if (index < 2) CardState.Matched else CardState.FaceDown,
                )
            },
            onCardTapped = {},
        )
    }
}
