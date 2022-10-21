package com.milanradosavac.textpad.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.milanradosavac.textpad.R

val Orbitron = FontFamily(
    Font(
        R.font.orbitron
    )
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Orbitron,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
)