package com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items

/**
 * The local file list screen
 * @param viewModel The viewmodel for this screen
 * @author Milan Radosavac
 */
@Composable
fun FileListScreen(viewModel: OnlineSyncViewModel) {
    Column(
        Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        LazyColumn(Modifier.clip(MaterialTheme.shapes.medium)) {
            items(
                items = viewModel.fileState.toList(),
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
                                viewModel.fileState[viewModel.fileState.indexOf(item)] = item.copy(
                                    isSynced = it
                                )
                            })
                        }
                        if(viewModel.fileState.indexOf(item) != viewModel.fileState.lastIndex) Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            )
        }
    }
}