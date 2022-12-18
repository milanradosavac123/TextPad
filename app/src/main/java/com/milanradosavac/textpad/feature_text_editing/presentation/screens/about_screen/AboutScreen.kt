package com.milanradosavac.textpad.feature_text_editing.presentation.screens.about_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.DrawerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milanradosavac.textpad.R
import com.milanradosavac.textpad.feature_text_editing.presentation.components.StandardAppBar
import com.milanradosavac.textpad.core.util.Constants.EMAIL_TAG
import kotlinx.coroutines.CoroutineScope

/**
 * Screen that contains info about me, this app, and the purpose of it's creation
 * @param scope The coroutine scope for this screen
 * @param drawerState The drawer state for the navigation drawer
 * @author Milan Radosavac
 */
@Composable
fun AboutScreen(
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    Column {
        StandardAppBar(modifier = Modifier.fillMaxWidth(), scope = scope, drawerState = drawerState)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp).fillMaxSize()
        ) {

            val annotatedLinkString: AnnotatedString = buildAnnotatedString {

                val appInfo = stringResource(id = R.string.app_info)
                val email = stringResource(id = R.string.email)
                val copyright = stringResource(id = R.string.copyright)
                append("$appInfo ")
                withStyle(SpanStyle(
                        color = Color(0xFFC80000),
                        fontSize = 20.sp,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(email)

                    addStringAnnotation(
                        tag = EMAIL_TAG,
                        annotation = "mailto:radosavacmilan03@gmail.com",
                        start = appInfo.lastIndex,
                        end = appInfo.lastIndex + email.lastIndex
                    )
                }
                append(copyright)

            }

            val uriHandler = LocalUriHandler.current

            ClickableText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = annotatedLinkString,
                style = MaterialTheme.typography.body2,
                onClick = {
                    annotatedLinkString
                        .getStringAnnotations(EMAIL_TAG, it, it)
                        .firstOrNull()?.let { stringAnnotation ->
                            uriHandler.openUri(stringAnnotation.item)
                        }
                }
            )
        }
    }
}