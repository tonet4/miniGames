package com.antonio.minigames.presentation.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.antonio.minigames.domain.menu.entity.MiniGame
import com.antonio.minigames.presentation.menu.GameItem
import com.antonio.minigames.presentation.menu.toGameItem
import com.antonio.minigames.presentation.shared.components.retroFrame
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun GameCard(
    item: GameItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .aspectRatio(0.85f)
            .retroFrame(containerColor = MaterialTheme.colorScheme.primaryContainer)
            .clickable(role = Role.Button, onClick = onClick),
    ) {
        AsyncImage(
            model = item.coverUrl,
            contentDescription = null,
            placeholder = painterResource(item.coverRes),
            error = painterResource(item.coverRes),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),
        )
        HorizontalDivider(thickness = 3.dp, color = MaterialTheme.colorScheme.outline)
        Text(
            text = stringResource(item.titleRes),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(12.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun GameCardPreview() {
    MinigamesTheme {
        Surface {
            GameCard(
                item = MiniGame.MEMORY.toGameItem(),
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 170.dp, height = 200.dp),
            )
        }
    }
}
