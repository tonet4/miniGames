package com.antonio.minigames.presentation.memory.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antonio.minigames.domain.memory.entity.CardState
import com.antonio.minigames.domain.memory.entity.MemoryBoard
import com.antonio.minigames.domain.memory.entity.MemoryCard
import com.antonio.minigames.domain.memory.entity.MemoryItem
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun MemoryGame(
    board: MemoryBoard,
    onCardTapped: (Int) -> Unit,
    onPlayAgain: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MemoryGrid(
            cards = board.cards,
            onCardTapped = onCardTapped,
            modifier = Modifier.weight(1f),
        )
        if (board.isFinished) {
            MemoryWinBar(onPlayAgain = onPlayAgain)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoryGamePreview() {
    MinigamesTheme {
        MemoryGame(
            board = MemoryBoard(
                cards = List(18) { index ->
                    MemoryCard(
                        id = index,
                        item = MemoryItem(id = "${index / 2}", name = "", imageUrl = ""),
                        state = CardState.Matched,
                    )
                },
            ),
            onCardTapped = {},
            onPlayAgain = {},
        )
    }
}
