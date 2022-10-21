package com.milanradosavac.textpad.feature_text_editing.presentation.screens.about_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.milanradosavac.textpad.feature_text_editing.presentation.components.StandardAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun AboutScreen(
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    StandardAppBar(modifier = Modifier.fillMaxWidth(), scope = scope, drawerState = drawerState)
}