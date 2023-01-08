package com.milanradosavac.textpad.core.util

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.DrawerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.OnlineSyncViewModel
import com.milanradosavac.textpad.feature_text_editing.presentation.screens.online_sync_management_screen.OnlineSyncManagementScreen
import com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen.MainScreen
import com.milanradosavac.textpad.feature_text_editing.presentation.screens.about_screen.AboutScreen
import com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen.MainViewModel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get

/**
 * The navigation drawer
 * @param navController The navigation controller
 * @param drawerState The navigation drawer state
 * @author Milan Radosavac
 */
@Composable
fun Navigation(
    navController: NavHostController,
    drawerState: DrawerState,
    activity: ComponentActivity
) {

    val scope = rememberCoroutineScope()
    val drawerShape = MaterialTheme.shapes.medium.copy(
        topStart = CornerSize(0.dp),
        bottomStart = CornerSize(0.dp)
    )

    ModalDrawer(
        drawerContent = {
            arrayOf(
                Screen.MainScreen,
                Screen.OnlineSyncManagementScreen,
                Screen.AboutScreen
            ).forEach { it ->

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.primary)
                        .clickable {
                            if (navController.currentDestination?.route != it()) {
                                navController.apply {
                                    navigate(it())

                                    scope.launch {
                                        drawerState.close()
                                    }

                                    return@clickable
                                }
                            }

                            scope.launch {
                                drawerState.close()
                            }
                        }
                ) {
                    Text(text = "- ${it.name}", color = MaterialTheme.colors.onPrimary, modifier = Modifier
                        .padding(start = 5.dp)
                    )
                }
            }
        },
        drawerShape = drawerShape,
        scrimColor = Color.Transparent,
        drawerState = drawerState
    ) {

        NavHost(
            navController = navController,
            startDestination = Screen.MainScreen()
        ) {

            composable(Screen.MainScreen()) { MainScreen(scope, drawerState, get(MainViewModel::class.java)) }

            composable(Screen.OnlineSyncManagementScreen()) { OnlineSyncManagementScreen(scope, drawerState, get(OnlineSyncViewModel::class.java), activity) }

            composable(Screen.AboutScreen()) { AboutScreen(scope, drawerState) }

        }

    }
}