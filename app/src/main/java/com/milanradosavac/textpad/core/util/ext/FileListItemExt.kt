package com.milanradosavac.textpad.core.util.ext

import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.model.dto.FileDto

/**
 * Map function that maps a [FileListItem] to a [FileDto] (File Data Transfer object)
 * used to store file info in the database
 * @author Milan Radosavac
 */
fun FileListItem.mapToDto(): FileDto {
    return FileDto(
        path = file.path,
        originalName = file.name,
        deviceOfOrigin = deviceOfOrigin,
        id = id
    )
}