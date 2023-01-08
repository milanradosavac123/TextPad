package com.milanradosavac.textpad.feature_online_sync.domain.remote.repos

import com.milanradosavac.textpad.feature_online_sync.data.remote.repos.DeviceRepositoryImpl
import com.milanradosavac.textpad.feature_online_sync.domain.model.Device
import io.ktor.client.*
import org.koin.java.KoinJavaComponent.inject

/**
 * [DeviceRepository] to interact with the remote sync api
 * @author Milan Radosavac
 */
interface DeviceRepository {

    /**
     * Adds the device to the remote sync api
     * @author Milan Radosavac
     */
    suspend fun addDevice(device: Device)

    /**
     * Removes the device from the remote sync api
     * @author Milan Radosavac
     */
    suspend fun removeDevice(deviceId: String)

    companion object {

        /**
         * The http client instance for the actual api interactions
         * @author Milan Radosavac
         */
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