package com.antonio.minigames.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

private fun readingStyle(size: TextUnit, lineHeight: TextUnit) = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontSize = size,
    lineHeight = lineHeight,
)

object ReadingTypography {
    val syllable = readingStyle(34.sp, 42.sp)
}
