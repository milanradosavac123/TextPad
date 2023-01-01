package com.milanradosavac.textpad.feature_online_sync.domain.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeviceAddOrRemoveRequest(
    val deviceId: String
)
