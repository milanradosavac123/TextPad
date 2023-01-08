package com.milanradosavac.textpad.core.util.ext

import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.model.dto.FileDto

fun FileListItem.mapToDto(): FileDto {
    return FileDto(
        path = file.path,
        originalName = file.name,
        deviceOfOrigin = deviceOfOrigin,
        id = id
    )
}