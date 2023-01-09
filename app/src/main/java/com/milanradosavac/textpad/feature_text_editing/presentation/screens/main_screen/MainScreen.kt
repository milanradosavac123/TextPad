package com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Redo
import androidx.compose.material.icons.outlined.Undo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.milanradosavac.textpad.R
import com.milanradosavac.textpad.feature_text_editing.presentation.components.StandardAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Screen that contains the main functionality of this app
 * @param scope The coroutine scope for this screen
 * @param drawerState The drawer state for the navigation drawer
 * @param viewModel The viewModel for this screen
 * @author Milan Radosavac
 */
@Composable
fun MainScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    Column {
        StandardAppBar(
            modifier = Modifier.fillMaxWidth(),
            scope = scope,
            drawerState = drawerState
        ) {

            IconButton(
                onClick = {
                    viewModel.undo()
                },
                enabled = viewModel.textFieldState.isNotBlank()
            ) {
                Icon(Icons.Outlined.Undo, "")
            }

            IconButton(
                onClick = {
                    viewModel.redo()
                },
                enabled = viewModel.redoStack.isNotEmpty()
            ) {
                Icon(Icons.Outlined.Redo, "")
            }

            IconButton(onClick = {
                if(viewModel.textChangeSinceLastSaveCount > 0) {
                    Toast.makeText(context, context.getText(R.string.file_changed_warning_alt), Toast.LENGTH_LONG).show()
                    return@IconButton
                }

                viewModel.onUriStateChanged()
                viewModel.onTextFieldStateChanged("")
            }) {
                Icon(Icons.Outlined.Create, "")
            }
        }
        Box {
            TextField(
                value = viewModel.textFieldState,
                onValueChange = {
                    viewModel.onTextFieldStateChanged(it)
                },
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.small.copy(CornerSize(0.dp)),
                modifier = Modifier
                    .fillMaxSize()
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val openFileLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
                    viewModel.onUriStateChanged(it)
                    val file = context.contentResolver.openInputStream(viewModel.uriState?: return@rememberLauncherForActivityResult)
                    val text = file?.readBytes()?.decodeToString() ?: "error"

                    scope.launch {
                        withContext(Dispatchers.IO) {
                            file?.close()
                        }
                    }
                    viewModel.onTextFieldStateChanged(text)
                    viewModel.resetTextChangeSinceLastSaveCount()
                }
                Button(onClick = {
                    openFileLauncher.launch(arrayOf("text/plain"))
                }) {
                    Text(
                        text = stringResource(id = R.string.open),
                        style = MaterialTheme.typography.body1
                    )
                }
                Button(onClick = {

                    if(viewModel.textFieldState.isEmpty()) return@Button

                    if(viewModel.textChangeSinceLastSaveCount > 0) {
                        Toast.makeText(context, context.getText(R.string.file_changed_warning), Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    viewModel.onUriStateChanged()
                    viewModel.clearStacks()
                    viewModel.onTextFieldStateChanged("")
                }) {
                    Text(
                        text = stringResource(id = R.string.close),
                        style = MaterialTheme.typography.body1
                    )
                }
                val saveFileLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.CreateDocument("text/plain")) {
                    val file = it?.let { it1 -> context.contentResolver.openOutputStream(it1) }
                    val text = viewModel.textFieldState
                    viewModel.onUriStateChanged(it)
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            file?.write(text.encodeToByteArray())
                            file?.close()
                        }
                    }
                }
                Button(onClick = {
                    viewModel.resetTextChangeSinceLastSaveCount()
                    viewModel.uriState?.let {
                        val file = context.contentResolver.openOutputStream(it)
                        val text = viewModel.textFieldState
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                file?.write(text.encodeToByteArray())
                                file?.close()
                            }
                        }
                        return@Button
                    }

                    saveFileLauncher.launch("")
                }) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}