package com.milanradosavac.textpad.feature_text_editing.presentation.screens.online_sync_management_screen

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.FileListScreen
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.OnlineSyncViewModel
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.server_setup_instructions_screen.ServerSetupInstructionsScreen
import com.milanradosavac.textpad.feature_text_editing.presentation.components.StandardAppBar
import com.milanradosavac.textpad.core.util.Constants.SERVER_URL_KEY
import kotlinx.coroutines.CoroutineScope
import org.koin.java.KoinJavaComponent.inject

/**
 * The screen that contains the online sync management functionality
 * @param scope The coroutine scope for this screen
 * @param drawerState The drawer state for the navigation drawer
 * @param viewModel The viewmodel for this screen
 * @author Milan Radosavac
 */
@Composable
fun OnlineSyncManagementScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: OnlineSyncViewModel
) {
    val sharedPreferences: SharedPreferences by inject(SharedPreferences::class.java)
    Column {
        StandardAppBar(modifier = Modifier.fillMaxWidth(), scope = scope, drawerState = drawerState)

        val serverLink = sharedPreferences.getString(SERVER_URL_KEY, "") ?: ""

        if (serverLink.isBlank()) {
            ServerSetupInstructionsScreen(viewModel)
            return@Column
        }

        FileListScreen(viewModel)
    }
}