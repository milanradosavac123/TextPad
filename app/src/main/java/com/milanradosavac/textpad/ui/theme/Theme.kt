package com.milanradosavac.textpad.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

/**
 * The colour pallete for the project
 * @author Milan Radosavac
 */
private val DarkColorPallete = darkColors(
    background = darkGray,
    onBackground = black,
    primary = red,
    primaryVariant = white,
    onPrimary = blue,
)

/**
 * The theme of the project
 * @author Milan Radosavac
 */
@Composable
fun TextPadTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPallete,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}