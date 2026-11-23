package com.antonio.minigames.presentation.syllables.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.antonio.minigames.presentation.shared.components.retroFrame
import com.antonio.minigames.ui.theme.MinigamesTheme
import com.antonio.minigames.ui.theme.ReadingTypography

@Composable
fun SyllableSlot(
    text: String?,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .retroFrame(
                containerColor = if (text == null) colors.surface else colors.primaryContainer,
                shadowOffset = 0.dp,
            )
            .defaultMinSize(minWidth = 76.dp, minHeight = 76.dp)
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text.orEmpty(),
            style = ReadingTypography.syllable,
            color = colors.onPrimaryContainer,
        )
    }
}

@PreviewLightDark
@Composable
private fun SyllableSlotPreview() {
    MinigamesTheme {
        Surface {
            SyllableSlot(text = "BO", modifier = Modifier.padding(16.dp))
        }
    }
}
