package com.antonio.minigames.presentation.memory.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.antonio.minigames.R
import com.antonio.minigames.domain.error.AppError
import com.antonio.minigames.presentation.shared.components.RetroButton
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun MemoryError(
    error: AppError,
    onRetry: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(error.toMessageRes()),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        RetroButton(
            text = stringResource(R.string.retry),
            onClick = onRetry,
        )
    }
}

@StringRes
private fun AppError.toMessageRes(): Int = when (this) {
    AppError.NoConnection -> R.string.error_no_connection
    AppError.ServerError -> R.string.error_server
    is AppError.Unexpected -> R.string.error_unexpected
}

@PreviewLightDark
@Composable
private fun MemoryErrorPreview() {
    MinigamesTheme {
        Surface {
            MemoryError(
                error = AppError.NoConnection,
                onRetry = {},
            )
        }
    }
}
