package com.antonio.minigames.presentation.syllables.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.antonio.minigames.R
import com.antonio.minigames.presentation.shared.components.retroFrame
import com.antonio.minigames.ui.theme.MinigamesTheme

@Composable
fun WordPhotoCard(
    @DrawableRes photoRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(photoRes),
        contentDescription = stringResource(R.string.listen_word),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .widthIn(max = 300.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .retroFrame()
            .clickable(role = Role.Button, onClick = onClick)
            .padding(3.dp),
    )
}

@PreviewLightDark
@Composable
private fun WordPhotoCardPreview() {
    MinigamesTheme {
        Surface {
            WordPhotoCard(
                photoRes = R.drawable.photo_gato,
                onClick = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
