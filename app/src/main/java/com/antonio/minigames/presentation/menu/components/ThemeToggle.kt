package com.antonio.minigames.presentation.menu.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.antonio.minigames.R
import com.antonio.minigames.domain.settings.entity.ThemeMode
import com.antonio.minigames.presentation.shared.components.retroFrame
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun ThemeToggle(
    isDarkTheme: Boolean,
    onThemeSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .retroFrame(containerColor = MaterialTheme.colorScheme.secondary)
            .clickable(role = Role.Button) {
                onThemeSelected(if (isDarkTheme) ThemeMode.LIGHT else ThemeMode.DARK)
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(if (isDarkTheme) R.drawable.ic_moon else R.drawable.ic_sun),
            contentDescription = stringResource(
                if (isDarkTheme) R.string.switch_to_light else R.string.switch_to_dark,
            ),
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.size(18.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun ThemeTogglePreview() {
    MinigamesTheme {
        Surface {
            ThemeToggle(
                isDarkTheme = false,
                onThemeSelected = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
