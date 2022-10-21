package com.milanradosavac.textpad.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPallete = darkColors(
    background = darkGray,
    onBackground = black,
    primary = red,
    primaryVariant = white,
    onPrimary = blue,
)

@Composable
fun TextPadTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = DarkColorPallete,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}