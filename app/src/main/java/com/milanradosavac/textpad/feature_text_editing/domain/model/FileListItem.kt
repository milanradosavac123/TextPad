package com.milanradosavac.textpad.feature_text_editing.domain.model

import java.io.File
import kotlin.reflect.KProperty

data class FileListItem(
    val file: File,
    val isSynced: Boolean = false
) {
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): FileListItem = this
}
