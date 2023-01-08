package com.milanradosavac.textpad.feature_online_sync.domain.model.requests

import kotlinx.serialization.Serializable

/**
 * Request data class that represents a device upload or deletion request
 * @param deviceId The id of the device being uploaded or deleted
 * @author Milan Radosavac
 */
@Serializable
data class DeviceAddOrRemoveRequest(
    val deviceId: String
)
