package com.antonio.minigames.presentation.memory.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.antonio.minigames.R
import com.antonio.minigames.domain.memory.entity.CardState
import com.antonio.minigames.domain.memory.entity.MemoryCard
import com.antonio.minigames.domain.memory.entity.MemoryItem
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun MemoryCardItem(
    card: MemoryCard,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRevealed = card.state != CardState.FaceDown
    val rotation by animateFloatAsState(
        targetValue = if (isRevealed) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "cardRotation",
    )
    val showFront = rotation > 90f
    val description = if (isRevealed) card.item.name else stringResource(R.string.memory_hidden_card)

    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .semantics { contentDescription = description },
        colors = CardDefaults.cardColors(
            containerColor = if (showFront) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.primary
            },
        ),
        border = if (card.state == CardState.Matched) {
            BorderStroke(5.dp, MaterialTheme.colorScheme.secondary)
        } else {
            BorderStroke(3.dp, MaterialTheme.colorScheme.outline)
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clearAndSetSemantics {},
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = card.item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .graphicsLayer { rotationY = 180f }
                    .alpha(if (showFront) 1f else 0f),
            )
            if (!showFront) {
                Text(
                    text = "?",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MemoryCardItemPreview() {
    MinigamesTheme {
        MemoryCardItem(
            card = MemoryCard(
                id = 0,
                item = MemoryItem(id = "0", name = "", imageUrl = ""),
            ),
            onClick = {},
            modifier = Modifier.size(160.dp),
        )
    }
}
