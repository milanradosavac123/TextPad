package com.milanradosavac.textpad.ki.tema

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.milanradosavac.textpad.R

val orbitron = FontFamily(
    Font(
        R.font.orbitron
    )
)

val Tipografija = Typography(
    body1 = TextStyle(
        fontFamily = orbitron,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
)