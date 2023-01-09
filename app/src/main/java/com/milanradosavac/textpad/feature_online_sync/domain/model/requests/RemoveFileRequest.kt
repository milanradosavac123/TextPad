package com.milanradosavac.textpad.feature_online_sync.domain.model.requests

import kotlinx.serialization.Serializable
import java.io.File

/**
 * Request data class that represents a request on the file deletion route
 * @param fileId The id of the [File] to remove
 * @author Milan Radosavac
 */
@Serializable
data class RemoveFileRequest(
    val fileId: String
)
