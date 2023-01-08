package com.milanradosavac.textpad.feature_online_sync.domain.model.responses

import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.*
import kotlinx.serialization.Serializable
import java.io.File

/**
 * Response data class that represents a response to an [AddFileRequest]
 * @param fileId The id of the added [File]'s info in the database
 * @author Milan Radosavac
 */
@Serializable
data class AddFileResponse(
    val fileId: String
)
