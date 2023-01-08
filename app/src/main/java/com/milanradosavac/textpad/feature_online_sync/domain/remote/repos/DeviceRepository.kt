package com.milanradosavac.textpad.feature_online_sync.domain.remote.repos

import com.milanradosavac.textpad.feature_online_sync.data.remote.repos.DeviceRepositoryImpl
import com.milanradosavac.textpad.feature_online_sync.domain.model.Device
import io.ktor.client.*
import org.koin.java.KoinJavaComponent.inject

interface DeviceRepository {

    suspend fun addDevice(device: Device)

    suspend fun removeDevice(deviceId: String)

    companion object {

        private val client: HttpClient by inject(HttpClient::class.java)

        /**
         * Device Repository generator
         * @return the generated repository
         * @author Milan Radosavac
         */
        fun create(): DeviceRepository {

            return DeviceRepositoryImpl(client)
        }
    }

}