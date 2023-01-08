package com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.milanradosavac.textpad.core.util.sdk30AndUp

/**
 * The local file list screen
 * @param viewModel The viewmodel for this screen
 * @author Milan Radosavac
 */
@Composable
fun FileListScreen(viewModel: OnlineSyncViewModel) {
    val context = LocalContext.current
    val managePermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.listTextFiles()
        }
    val permissionRequestLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            viewModel.listTextFiles()
        }
    sdk30AndUp {
        val intent = Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        SideEffect {
            if (!Environment.isExternalStorageManager()) {
                managePermissionLauncher.launch(intent)
            }
        }
    } ?: permissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    sdk30AndUp {
        if (Environment.isExternalStorageManager()) {
            remember {
                viewModel.listTextFiles()
                null
            }
        }
    }?: if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        remember {
            viewModel.listTextFiles()
            null
        }
    } else Unit
    Column(
        Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {

        LazyColumn(Modifier.clip(MaterialTheme.shapes.medium)) {
            items(
                items = viewModel.fileState.toList().distinct(),
                itemContent = { item ->
                    Column {
                        Row(
                            Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.medium)
                                .background(Color.Black),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = item.file.name,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.primaryVariant,
                                modifier = Modifier.weight(1F)
                            )
                            Checkbox(checked = item.isSynced, onCheckedChange = {
                                if(it) {
                                    viewModel.addFile(viewModel.fileState.indexOf(item))
                                }
                                viewModel.fileState[viewModel.fileState.indexOf(item)] = item.copy(
                                    isSynced = it
                                )
                            })
                        }
                        if (viewModel.fileState.indexOf(item) != viewModel.fileState.lastIndex) Spacer(
                            modifier = Modifier.height(5.dp)
                        )
                    }
                }
            )
        }
    }
}