package com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Redo
import androidx.compose.material.icons.outlined.Undo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.milanradosavac.textpad.R
import com.milanradosavac.textpad.feature_text_editing.presentation.components.StandardAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    Column {
        StandardAppBar(
            modifier = Modifier.fillMaxWidth(),
            scope = scope,
            drawerState = drawerState
        ) {

            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Undo, "")
            }

            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Redo, "")
            }

            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Create, "")
            }

            IconButton(onClick = { }) {
                Icon(Icons.Outlined.DeleteOutline, "")
            }
        }
        Box {

            var testState by remember {
                mutableStateOf(
                    "Lorem ipsum dolor sit amet, " +
                            "consectetur adipiscing elit. Proin enim, " +
                            "mattis eget massa quam mattis morbi. " +
                            "Eget donec vestibulum, varius urna. " +
                            "Non at parturient etiam egestas tincidunt. " +
                            "Purus metus, et ut egestas. " +
                            "Non ullamcorper sem risus scelerisque curabitur. " +
                            "Sed risus faucibus gravida vel, " +
                            "urna consequat lectus nec fringilla. " +
                            "Lacinia fermentum eget faucibus at lorem. " +
                            "Mauris tellus sit amet," +
                            "pellentesque. Consectetur et, " +
                            "at neque faucibus ipsum elit mi dignissim." +
                            "Pellentesque pellentesque eu massa aliquam. " +
                            "At dui sit id amet ornare. " +
                            "Sit quisque odio a id id eu et, " +
                            "id. Mi, massa at eu, pretium. " +
                            "Et pellentesque est nibh ac aliquam. " +
                            "Nunc, venenatis elit, in sed sed amet, " +
                            "sed feugiat. Et amet consequat ut adipiscing. " +
                            "Lorem ipsum dolor sit amet, " +
                            "consectetur adipiscing elit. Proin enim, " +
                            "mattis eget massa quam mattis morbi. " +
                            "Eget donec vestibulum, varius urna. " +
                            "Non at parturient etiam egestas tincidunt. " +
                            "Purus metus, et ut egestas. " +
                            "Non ullamcorper sem risus scelerisque curabitur. " +
                            "Sed risus faucibus gravida vel, " +
                            "urna consequat lectus nec fringilla. " +
                            "Lacinia fermentum eget faucibus at lorem. " +
                            "Mauris tellus sit amet," +
                            "pellentesque. Consectetur et, " +
                            "at neque faucibus ipsum elit mi dignissim." +
                            "Pellentesque pellentesque eu massa aliquam. " +
                            "At dui sit id amet ornare. " +
                            "Sit quisque odio a id id eu et, " +
                            "id. Mi, massa at eu, pretium. " +
                            "Et pellentesque est nibh ac aliquam. " +
                            "Nunc, venenatis elit, in sed sed amet, " +
                            "sed feugiat. Et amet consequat ut adipiscing."
                )
            }
            TextField(
                value = testState,
                onValueChange = {
                    testState = it
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
                Button(onClick = { }) {
                    Text(
                        text = stringResource(id = R.string.open),
                        style = MaterialTheme.typography.body1
                    )
                }
                Button(onClick = { }) {
                    Text(
                        text = stringResource(id = R.string.close),
                        style = MaterialTheme.typography.body1
                    )
                }
                Button(onClick = { }) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}