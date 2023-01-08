package com.milanradosavac.textpad.feature_online_sync.presentation.screens.server_setup_instructions_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milanradosavac.textpad.R
import com.milanradosavac.textpad.feature_online_sync.presentation.dialogs.server_setup_dialog.OnlineSyncServerSetupDialog
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.OnlineSyncViewModel
import com.milanradosavac.textpad.core.util.Constants

/**
 * The server setup instructions screen
 * @param viewModel The viewmodel for this screen
 * @author Milan Radosavac
 */
@Composable
fun ServerSetupInstructionsScreen(
    viewModel: OnlineSyncViewModel
) {
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
                    tag = Constants.URL_TAG,
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
                .getStringAnnotations(Constants.URL_TAG, it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }

        Button(onClick = { viewModel.onDialogStateChanged(true) }) {
            Text(
                text = stringResource(id = R.string.add_server),
                style = MaterialTheme.typography.body1
            )
        }

        val context = LocalContext.current
        OnlineSyncServerSetupDialog(
            showDialog = viewModel.dialogState,
            serverUrl = viewModel.serverUrlTextState,
            onShowDialogChanged = {
                viewModel.onDialogStateChanged(it)
                viewModel.saveServerUrl()
                Toast.makeText(context, context.getString(R.string.server_url_saved_notice), Toast.LENGTH_LONG).show()
                viewModel.addDevice()
                viewModel.onServerUrlTextStateChanged("")
            }
        ) {
            viewModel.onServerUrlTextStateChanged(it)
        }
    }
}