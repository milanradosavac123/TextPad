package com.milanradosavac.textpad.feature_text_editing.domain.model

import java.io.File
import kotlin.reflect.KProperty

/**
 * Class that describes an item in the list of files on the user's device
 * @param file The actual file
 * @param isSynced If that file is synced or not
 * @author Milan Radosavac
 */
data class FileListItem(
    val file: File,
    val isSynced: Boolean = false
) {
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): FileListItem = this
}
