package com.milanradosavac.textpad.feature_text_editing.presentation.screens.online_sync_management_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.milanradosavac.textpad.R
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.FileListScreen
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.OnlineSyncViewModel
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.server_setup_instructions_screen.ServerSetupInstructionsScreen
import com.milanradosavac.textpad.feature_text_editing.presentation.components.StandardAppBar
import kotlinx.coroutines.CoroutineScope

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
    val context = LocalContext.current
    Column {
        StandardAppBar(modifier = Modifier.fillMaxWidth(), scope = scope, drawerState = drawerState) {
            IconButton(
                onClick = {
                    viewModel.removeDevice()
                    Toast.makeText(context, context.getString(R.string.server_url_removed_notice), Toast.LENGTH_LONG).show()
                    viewModel.onServerUrlStateChanged("")
                    viewModel.removeServerUrl()
                },
                enabled = !viewModel.serverUrlState.isNullOrBlank()
            ) {
                Icon(Icons.Outlined.RemoveCircleOutline, contentDescription = "")
            }
        }

        if (viewModel.serverUrlState.isNullOrBlank()) {
            ServerSetupInstructionsScreen(viewModel)
            return@Column
        }

        FileListScreen(viewModel)
    }
}