package com.milanradosavac.textpad.feature_online_sync.presentation.dialogs.server_setup_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.milanradosavac.textpad.R

/**
 * The dialog for setting up the server
 * @param showDialog The state that determines if the dialog should be shown or not
 * @param serverUrl The state that holds the server url string
 * @param onShowDialogChanged The callback that handles showDialogState changes
 * @param onServerUrlTextChanged The callback that handles serverUrlState changes
 * @author Milan Radosavac
 */
@Composable
fun OnlineSyncServerSetupDialog(
    showDialog: Boolean,
    serverUrl: String,
    onShowDialogChanged: (it: Boolean) -> Unit,
    onServerUrlTextChanged: (it: String) -> Unit
) {
    if(showDialog) {
        Dialog(onDismissRequest = { onShowDialogChanged(false) }) {
            Column(
                Modifier
                    .padding(15.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        MaterialTheme.colors.background
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = serverUrl,
                    onValueChange = onServerUrlTextChanged,
                    textStyle = MaterialTheme.typography.body1, label = {
                        Text(text = stringResource(id = R.string.enter_url), style = MaterialTheme.typography.body1)
                    },
                    modifier = Modifier.padding(15.dp),
                    singleLine = true
                )
                Button(
                    onClick = {
                        onShowDialogChanged(false)
                    },
                    modifier = Modifier.padding(bottom = 15.dp)
                ) {
                    Text(text = stringResource(id = R.string.add_server), style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}