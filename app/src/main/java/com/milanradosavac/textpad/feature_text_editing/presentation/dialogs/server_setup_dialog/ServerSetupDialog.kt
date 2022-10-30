package com.milanradosavac.textpad.feature_text_editing.presentation.dialogs.server_setup_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.milanradosavac.textpad.R

@Composable
fun OnlineSyncServerSetupDialog(
    showDialogState: State<Boolean>,
    serverUrlState: State<String>,
    onShowDialogChanged: (it: Boolean) -> Unit,
    onServerUrlTextChanged: (it: String) -> Unit
) {
    if(showDialogState.value) {
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
                    value = serverUrlState.value,
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