package com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.core.content.edit
import com.milanradosavac.textpad.core.util.Constants.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY
import com.milanradosavac.textpad.core.util.sdk30AndUp
import com.milanradosavac.textpad.R as r

/**
 * The local file list screen
 * @param viewModel The viewmodel for this screen
 * @param activity The root activity of all the screens
 * @author Milan Radosavac
 */
@Composable
fun FileListScreen(
    viewModel: OnlineSyncViewModel,
    activity: ComponentActivity
) {
    lateinit var permissionRequestLauncher: ManagedActivityResultLauncher<String, Boolean>
    val context = LocalContext.current

    val alertDialog = AlertDialog.Builder(context).apply {
        setTitle(r.string.permission_rationale_title)
        setMessage(r.string.permission_rationale_message)
        setPositiveButton("OK", ) { _, _ ->
            permissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }.create()

    val managePermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.listTextFiles()
            viewModel.loadFilesFromLocalDatabase()
        }

    val permissionPermanentlyDeniedInfo = stringResource(id = r.string.permission_permanently_denied)
    permissionRequestLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            viewModel.sharedPreferences.edit {
                putInt(READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY, viewModel.sharedPreferences.getInt(
                    READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY, 0) + 1
                )
            }
            if (it) {
                viewModel.listTextFiles()
                viewModel.loadFilesFromLocalDatabase()
                return@rememberLauncherForActivityResult
            }

            if(activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                && viewModel.sharedPreferences.getInt(READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY, 0) > 1) {
                alertDialog.show()
                return@rememberLauncherForActivityResult
            }

            if(viewModel.sharedPreferences.getInt(READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY, 0) > 1) {
                Toast.makeText(context, permissionPermanentlyDeniedInfo, Toast.LENGTH_LONG).show()
            }
        }

    sdk30AndUp {
        val intent = Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        SideEffect {
            if (!Environment.isExternalStorageManager()) {
                managePermissionLauncher.launch(intent)
            }
        }
    } ?: SideEffect {
        permissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    sdk30AndUp {
        if (Environment.isExternalStorageManager()) {
            remember {
                viewModel.listTextFiles()
                viewModel.loadFilesFromLocalDatabase()
                null
            }
        }
    } ?: if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        remember {
            viewModel.listTextFiles()
            viewModel.loadFilesFromLocalDatabase()
            null
        }
    } else Unit

    Column(
        Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {

        LazyColumn(Modifier.clip(MaterialTheme.shapes.medium)) {
            if(viewModel.localDatabaseFileLoadingProgressState) {
                item {
                    CircularProgressIndicator()
                }
            }
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
                            if(viewModel.fileAddingOrRemovingProgressState[viewModel.fileState.indexOf(item)]) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            }
                            Checkbox(checked = item.isSynced, onCheckedChange = {
                                if (it) {
                                    viewModel.addFile(viewModel.fileState.indexOf(item))
                                }
                                if(!it) {
                                    viewModel.removeFile(viewModel.fileState.indexOf(item))
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