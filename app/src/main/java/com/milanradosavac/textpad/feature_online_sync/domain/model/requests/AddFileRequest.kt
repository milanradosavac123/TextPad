package com.milanradosavac.textpad.feature_online_sync.domain.model.requests

import java.io.File
import com.milanradosavac.textpad.feature_online_sync.domain.model.*
import kotlinx.serialization.Serializable

/**
 * Request data class that represents a [File] upload request
 * @param originalName The name of the [File] on the user's [Device]
 * @param deviceOfOrigin The id of the user's [Device] the [File] comes from
 * @param fileId The id of the [File] being uploaded(if this is not the first time uploading said [File] to the server)
 * @author Milan Radosavac
 */
@Serializable
data class AddFileRequest(
    val originalName: String,
    val deviceOfOrigin: String,
    val fileId: String? = null
)
