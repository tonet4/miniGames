package com.antonio.minigames.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.antonio.minigames.R

val PixelFontFamily = FontFamily(Font(R.font.press_start_2p))
val TerminalFontFamily = FontFamily(Font(R.font.vt323))

private fun pixelStyle(size: TextUnit, lineHeight: TextUnit) = TextStyle(
    fontFamily = PixelFontFamily,
    fontSize = size,
    lineHeight = lineHeight,
)

private fun terminalStyle(size: TextUnit, lineHeight: TextUnit) = TextStyle(
    fontFamily = TerminalFontFamily,
    fontSize = size,
    lineHeight = lineHeight,
)

val Typography = Typography(
    displayLarge = pixelStyle(40.sp, 52.sp),
    displayMedium = pixelStyle(34.sp, 44.sp),
    displaySmall = pixelStyle(26.sp, 36.sp),
    headlineLarge = pixelStyle(24.sp, 34.sp),
    headlineMedium = pixelStyle(18.sp, 28.sp),
    headlineSmall = pixelStyle(16.sp, 24.sp),
    titleLarge = pixelStyle(16.sp, 24.sp),
    titleMedium = pixelStyle(14.sp, 22.sp),
    titleSmall = pixelStyle(12.sp, 18.sp),
    bodyLarge = terminalStyle(24.sp, 28.sp),
    bodyMedium = terminalStyle(20.sp, 24.sp),
    bodySmall = terminalStyle(18.sp, 22.sp),
    labelLarge = terminalStyle(24.sp, 28.sp),
    labelMedium = terminalStyle(20.sp, 24.sp),
    labelSmall = terminalStyle(18.sp, 22.sp),
)
