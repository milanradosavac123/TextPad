package com.milanradosavac.textpad.feature_online_sync.domain.model

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
    val isSynced: Boolean = false,
    val deviceOfOrigin: String,
    val id: String
) {
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): FileListItem = this
}
