package com.antonio.minigames.presentation.memory.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.antonio.minigames.R
import com.antonio.minigames.presentation.shared.components.RetroButton
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun MemoryWinBar(
    onPlayAgain: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.memory_win),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f),
        )
        RetroButton(
            text = stringResource(R.string.play_again),
            onClick = onPlayAgain,
        )
    }
}

@PreviewLightDark
@Composable
private fun MemoryWinBarPreview() {
    MinigamesTheme {
        Surface {
            MemoryWinBar(onPlayAgain = {})
        }
    }
}
