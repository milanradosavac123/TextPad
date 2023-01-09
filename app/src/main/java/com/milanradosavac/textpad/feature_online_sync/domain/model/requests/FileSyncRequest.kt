package com.milanradosavac.textpad.feature_online_sync.domain.model.requests

import com.milanradosavac.textpad.feature_online_sync.domain.model.*
import kotlinx.serialization.Serializable
import java.io.File

/**
 * Request data class that represents a [File] synchronisation operation (beginning synchronising or performing the actual synchronisation, etc.)
 * @param deviceId The id of the [Device] the [File] synchronisation operation is performed on
 * @param fileId The id of the [File] the synchronisation operation is being performed on
 * (null if said operation is to be performed on every file in the database)
 * @author Milan Radosavac
 */
@Serializable
data class FileSyncRequest(
    val deviceId: String,
    val fileId: String? = null
)
