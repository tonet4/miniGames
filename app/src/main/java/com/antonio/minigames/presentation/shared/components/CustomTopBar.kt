package com.antonio.minigames.presentation.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.antonio.minigames.R
import com.antonio.minigames.ui.theme.MinigamesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    onNavigateLeftIcon: () -> Unit = {},
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            },
            navigationIcon = {
                IconButton(onClick = onNavigateLeftIcon) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
            ),
        )
        HorizontalDivider(thickness = 3.dp, color = MaterialTheme.colorScheme.outline)
    }
}

@PreviewLightDark
@Composable
private fun CustomTopBarPreview() {
    MinigamesTheme {
        CustomTopBar(title = "Memory")
    }
}
