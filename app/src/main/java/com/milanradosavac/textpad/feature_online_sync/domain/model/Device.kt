package com.milanradosavac.textpad.feature_online_sync.domain.model

/**
 * The [Device] data class represents [Device]s, or more accurately,
 * what they look like in the database
 * @param deviceId The [deviceId] string used to describe and differentiate between each [Device]
 * @author Milan Radosavac
 */
data class Device(
    val deviceId: String
)
