package com.milanradosavac.textpad.feature_online_sync.domain.remote.services

import com.milanradosavac.textpad.feature_online_sync.data.remote.services.DeviceServiceImpl
import com.milanradosavac.textpad.feature_online_sync.domain.model.Device
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface DeviceService {

    suspend fun addDevice(device: Device)

    suspend fun removeDevice(deviceId: String)

    companion object {
        fun create(): DeviceService {

            return DeviceServiceImpl(
                HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }

}