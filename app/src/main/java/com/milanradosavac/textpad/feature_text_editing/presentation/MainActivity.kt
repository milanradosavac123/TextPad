package com.milanradosavac.textpad.feature_text_editing.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.milanradosavac.textpad.feature_text_editing.presentation.util.Navigation
import com.milanradosavac.textpad.ui.theme.TextPadTheme

/**
 * Main Activity class
 * @author Milan Radosavac
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextPadTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(navController = rememberNavController(), drawerState = rememberDrawerState(initialValue = DrawerValue.Closed))
                }
            }
        }
    }
}