package com.milanradosavac.textpad.ki.tema

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val TamnaPaletaBoja = darkColors(
    background = tamnoSiva,
    onBackground = crna,
    primary = crvena,
    primaryVariant = bijela,
    onPrimary = plava,
)

@Composable
fun TextPadTema(sadrzaj: @Composable () -> Unit) {

    MaterialTheme(
        colors = TamnaPaletaBoja,
        typography = Tipografija,
        shapes = Oblici,
        content = sadrzaj
    )

}