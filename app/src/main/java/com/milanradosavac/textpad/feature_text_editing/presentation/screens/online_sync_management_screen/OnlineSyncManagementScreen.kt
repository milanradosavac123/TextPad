package com.milanradosavac.textpad.feature_text_editing.presentation.screens.online_sync_management_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milanradosavac.textpad.R
import com.milanradosavac.textpad.feature_text_editing.domain.model.FileListItem
import com.milanradosavac.textpad.feature_text_editing.presentation.components.StandardAppBar
import com.milanradosavac.textpad.feature_text_editing.presentation.dialogs.server_setup_dialog.OnlineSyncServerSetupDialog
import com.milanradosavac.textpad.feature_text_editing.presentation.util.Constants.URL_TAG
import kotlinx.coroutines.CoroutineScope

/**
 * The screen that contains the online sync management functionality
 * @param scope The coroutine scope for this screen
 * @param drawerState The drawer state for the navigation drawer
 * @author Milan Radosavac
 */
@Composable
fun OnlineSyncManagementScreen(
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    Column {
        StandardAppBar(modifier = Modifier.fillMaxWidth(), scope = scope, drawerState = drawerState)
        val fileState = remember {
            mutableStateListOf<FileListItem>()
        }

//        remember {
//            fileState.add(FileListItem(file = File("/document:Documents/foo.txt"), isSynced = true))
//            fileState.add(FileListItem(file = File("/document:Documents/bar.txt"), isSynced = true))
//            fileState.add(FileListItem(file = File("/document:Documents/baz.txt")))
//            fileState.add(FileListItem(file = File("/document:Documents/lorem.txt")))
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/ipsum.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/dolor.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(FileListItem(file = File("/document:Documents/sit.txt"), isSynced = true))
//            fileState.add(FileListItem(file = File("/document:Documents/amet.txt")))
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/consecteur.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(FileListItem(file = File("/document:Documents/adipiscing.txt")))
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/elit.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/proin.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/enim.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/mattis.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(FileListItem(file = File("/document:Documents/eget.txt")))
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/massa.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/quam.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/morbi.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(FileListItem(file = File("/document:Documents/eget.txt")))
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/donec.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/vestibulum.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/varius.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(
//                FileListItem(
//                    file = File("/document:Documents/urna.txt"),
//                    isSynced = true
//                )
//            )
//            fileState.add(FileListItem(file = File("/document:Documents/tincidunt.txt")))
//        }
        if (fileState.isEmpty()) {
            SetupInstructions()
            return@Column
        }

        FileList(fileState)
    }
}

@Composable
private fun FileList(fileState: SnapshotStateList<FileListItem>) {
    Column(
        Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        LazyColumn(Modifier.clip(MaterialTheme.shapes.medium)) {
            items(
                items = fileState,
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
                                modifier = Modifier.weight(1F)                            )
                            Checkbox(checked = item.isSynced, onCheckedChange = {
                                fileState[fileState.indexOf(item)] = item.copy(
                                    isSynced = it
                                )
                            })
                        }
                        if(fileState.indexOf(item) != fileState.lastIndex) Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            )
        }
    }
}

@Composable
private fun SetupInstructions() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        val serverAddingInstructionsPart1 = "${stringResource(id = R.string.server_adding_instructions_part_1)} "
        val websiteUrl = stringResource(id = R.string.website_url)
        val serverAddingInstructionsPart2 = " ${stringResource(id = R.string.server_adding_instructions_part_2)}"

        val serverAddingInstructionsString = buildAnnotatedString {
            append(serverAddingInstructionsPart1)
            withStyle(
                SpanStyle(
                    color = Color(0xFFC80000),
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(websiteUrl)

                addStringAnnotation(
                    tag = URL_TAG,
                    annotation = "https://$websiteUrl",
                    start = serverAddingInstructionsPart1.lastIndex,
                    end = serverAddingInstructionsPart1.lastIndex + websiteUrl.lastIndex
                )
            }
            append(serverAddingInstructionsPart2)
        }

        val uriHandler = LocalUriHandler.current

        ClickableText(
            text = serverAddingInstructionsString,
            style = MaterialTheme.typography.body1
        ) {
            serverAddingInstructionsString
                .getStringAnnotations(URL_TAG, it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }

        val dialogState = remember {
            mutableStateOf(false)
        }

        val serverUrlState = remember {
            mutableStateOf("")
        }

        Button(onClick = { dialogState.value = true }) {
            Text(
                text = stringResource(id = R.string.add_server),
                style = MaterialTheme.typography.body1
            )
        }

        OnlineSyncServerSetupDialog(
            showDialogState = dialogState,
            serverUrlState = serverUrlState,
            onShowDialogChanged = {
                dialogState.value = it
            }
        ) {
            serverUrlState.value = it
        }
    }
}