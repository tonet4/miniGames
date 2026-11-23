package com.antonio.minigames.presentation.syllables.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.antonio.minigames.presentation.shared.components.retroFrame
import com.antonio.minigames.ui.theme.MinigamesTheme
import com.antonio.minigames.ui.theme.ReadingTypography

@Composable
fun SyllableTileItem(
    text: String,
    isPlaced: Boolean,
    isWrong: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .alpha(if (isPlaced) 0f else 1f)
            .retroFrame(containerColor = if (isWrong) colors.tertiary else colors.secondary)
            .clickable(enabled = !isPlaced, role = Role.Button, onClick = onClick)
            .defaultMinSize(minWidth = 76.dp, minHeight = 76.dp)
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = ReadingTypography.syllable,
            color = if (isWrong) colors.onTertiary else colors.onSecondary,
        )
    }
}

@PreviewLightDark
@Composable
private fun SyllableTileItemPreview() {
    MinigamesTheme {
        Surface {
            SyllableTileItem(
                text = "BO",
                isPlaced = false,
                isWrong = false,
                onClick = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
