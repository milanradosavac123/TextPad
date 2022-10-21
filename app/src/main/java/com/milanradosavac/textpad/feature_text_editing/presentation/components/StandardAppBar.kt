package com.milanradosavac.textpad.feature_text_editing.presentation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.milanradosavac.textpad.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun StandardAppBar(
    modifier: Modifier,
    scope: CoroutineScope,
    drawerState: DrawerState,
    options: (@Composable () -> Unit)? = null
) {
    // Call the TopAppBar composable
    TopAppBar(
        // Set the title to <app_name>
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        // Set the modifier
        modifier = modifier,
        // Set the navigation icon to an icon button
        navigationIcon = {
            IconButton(onClick = {

                /*
                 * On click of the aforementioned icon button, launch a coroutine
                 * using the scope parameter
                 */
                scope.launch {
                    // Open the drawer
                    drawerState.open()
                }
            }
            ) {
                // Set the icon for the icon button
                Icon(Icons.Default.Menu, "menu")
            }
        },
        // Set the background and content colours + the options icons(if necessary)
        backgroundColor = MaterialTheme.colors.onBackground,
        contentColor = MaterialTheme.colors.primaryVariant,
        actions = {
            if (options == null) {
                return@TopAppBar
            }

            options()
        }
    )
}
