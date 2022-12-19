package com.milanradosavac.textpad.feature_text_editing.presentation.screens.online_sync_management_screen

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.milanradosavac.textpad.R
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
    val context = LocalContext.current
    Column {
        StandardAppBar(modifier = Modifier.fillMaxWidth(), scope = scope, drawerState = drawerState) {
            IconButton(
                onClick = {
                    sharedPreferences.edit().remove(SERVER_URL_KEY).apply()
                    Toast.makeText(context, context.getString(R.string.server_url_removed_notice), Toast.LENGTH_LONG).show()
                },
                enabled = !sharedPreferences.getString(SERVER_URL_KEY, "").isNullOrBlank()
            ) {
                Icon(Icons.Outlined.RemoveCircleOutline, contentDescription = "")
            }
        }

        val serverLink = sharedPreferences.getString(SERVER_URL_KEY, "")

        if (serverLink.isNullOrBlank()) {
            ServerSetupInstructionsScreen(viewModel)
            return@Column
        }

        FileListScreen(viewModel)
    }
}