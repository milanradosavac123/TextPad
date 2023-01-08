package com.milanradosavac.textpad.feature_online_sync.data.remote.repos

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.milanradosavac.textpad.core.util.Constants.DEBUG_TAG
import com.milanradosavac.textpad.core.util.Constants.DEVICE_ID_KEY
import com.milanradosavac.textpad.core.util.Constants.HttpRoutes.ROUTE_DEVICE_ADD
import com.milanradosavac.textpad.core.util.Constants.HttpRoutes.ROUTE_DEVICE_REMOVE
import com.milanradosavac.textpad.core.util.Constants.SERVER_URL_KEY
import com.milanradosavac.textpad.feature_online_sync.domain.model.Device
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.DeviceAddOrRemoveRequest
import com.milanradosavac.textpad.feature_online_sync.domain.remote.repos.DeviceRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.java.KoinJavaComponent.inject

class DeviceRepositoryImpl(
    private val client: HttpClient
): DeviceRepository {

    private val sharedPreferences: SharedPreferences by inject(SharedPreferences::class.java)

    private val BASE_URL = sharedPreferences.getString(SERVER_URL_KEY, "error") ?: "error"

    override suspend fun addDevice(device: Device) {
        sharedPreferences.edit {
            putString(DEVICE_ID_KEY, device.deviceId)
        }
        try {
            client.post<Unit> {
                url("$BASE_URL$ROUTE_DEVICE_ADD")
                contentType(ContentType.Application.Json)
                body = DeviceAddOrRemoveRequest(
                    device.deviceId
                )
            }
        } catch(e: RedirectResponseException) {
            Log.d("$DEBUG_TAG: Server Exception1","Error: ${e.response.status.description}")
        } catch(e: ClientRequestException) {
            Log.d("$DEBUG_TAG: Server Exception2","Error: ${e.response.status.description}")
        } catch(e: ServerResponseException) {
            Log.d("$DEBUG_TAG: Server Exception3","Error: ${e.response.status.description}")
        } catch(e: Exception) {
            Log.d("$DEBUG_TAG: Server Exception4","Error: ${e.stackTrace.asList()}")
        }
    }

    override suspend fun removeDevice(deviceId: String) {
        try {
            client.delete<Unit> {
                url("$BASE_URL$ROUTE_DEVICE_REMOVE")
                contentType(ContentType.Application.Json)
                body = DeviceAddOrRemoveRequest(
                    deviceId
                )
            }
        } catch(e: RedirectResponseException) {
            Log.d("$DEBUG_TAG: Server Exception1: ","Error: ${e.response.status.description}")
        } catch(e: ClientRequestException) {
            Log.d("$DEBUG_TAG: Server Exception2: ","Error: ${e.response.status.description}")
        } catch(e: ServerResponseException) {
            Log.d("$DEBUG_TAG: Server Exception3: ","Error: ${e.response.status.description}")
        } catch(e: Exception) {
            Log.d("$DEBUG_TAG: Server Exception4: ","Error: ${e.stackTrace.asList()}")
        }
    }
}