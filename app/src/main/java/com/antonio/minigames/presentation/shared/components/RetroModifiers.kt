package com.antonio.minigames.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val DOT_ALPHA = 0.12f

@Composable
fun Modifier.retroBackground(): Modifier {
    val background = MaterialTheme.colorScheme.background
    val dot = MaterialTheme.colorScheme.outline.copy(alpha = DOT_ALPHA)
    return this.drawBehind {
        drawRect(background)
        val step = 16.dp.toPx()
        val dotSize = Size(2.dp.toPx(), 2.dp.toPx())
        var y = step / 2
        while (y < size.height) {
            var x = step / 2
            while (x < size.width) {
                drawRect(color = dot, topLeft = Offset(x, y), size = dotSize)
                x += step
            }
            y += step
        }
    }
}

@Composable
fun Modifier.retroFrame(
    containerColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    shape: Shape = MaterialTheme.shapes.medium,
    borderWidth: Dp = 3.dp,
    shadowOffset: Dp = 4.dp,
): Modifier = this
    .drawBehind {
        val offset = shadowOffset.toPx()
        translate(left = offset, top = offset) {
            drawOutline(shape.createOutline(size, layoutDirection, this), borderColor)
        }
    }
    .clip(shape)
    .background(containerColor)
    .border(borderWidth, borderColor, shape)
