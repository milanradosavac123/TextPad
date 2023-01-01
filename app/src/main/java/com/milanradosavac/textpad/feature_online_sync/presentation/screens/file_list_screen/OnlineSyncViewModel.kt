package com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milanradosavac.textpad.core.util.Constants.DEVICE_ID_KEY
import com.milanradosavac.textpad.core.util.Constants.SERVER_URL_KEY
import com.milanradosavac.textpad.feature_online_sync.domain.model.Device
import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.remote.services.DeviceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID

/**
 * The [OnlineSyncViewModel] for the online sync screens
 * @author Milan Radosavac
 */
class OnlineSyncViewModel: ViewModel() {

    /**
     * Device service used to make the device network requests
     * @author MaskedRedstonerProZ
     */
    private val deviceService: DeviceService by inject(DeviceService::class.java)

    /**
     * Shared preferences object used to store data like the server url
     * @author Milan Radosavac
     */
    private val sharedPreferences: SharedPreferences by inject(SharedPreferences::class.java)

    /**
     * The state object that holds the file list state
     * @author Milan Radosavac
     */
    val fileState = mutableStateListOf<FileListItem>()

    /**
     * The state object that holds the dialog visibility state
     * @author Milan Radosavac
     */
    var dialogState by mutableStateOf(false)
        private set

    /**
     * The state object that holds the server url text state
     * @author Milan Radosavac
     */
    var serverUrlState by mutableStateOf("")
        private set

    /**
     * Save server url event handler
     * @author Milan Radosavac
     */
    fun saveServerUrl() {
        sharedPreferences.edit().putString(SERVER_URL_KEY, serverUrlState).apply()
    }

    /**
     * Remove server url event handler
     * @author MaskedRedstonerProZ
     */
    fun removeServerUrl() {
        sharedPreferences.edit().remove(SERVER_URL_KEY).apply()
    }

    /**
     * The add device event handler
     * @author Milan Radosavac
     */
    fun addDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            deviceService.addDevice(
                Device(
                    UUID.randomUUID().toString()
                )
            )
        }
    }

    /**
     * The remove device event handler
     * @author Milan Radosavac
     */
    fun removeDevice() {
        viewModelScope.launch {
            deviceService.removeDevice(
                sharedPreferences.getString(DEVICE_ID_KEY, "error") ?: "error"
            )
        }
    }

    /**
     * Public-friendly server url text state setter
     * @author Milan Radosavac
     */
    fun onServerUrlStateChanged(it: String) {
        serverUrlState = it
    }

    /**
     * Public-friendly dialog visibility state setter
     * @author Milan Radosavac
     */
    fun onDialogStateChanged(it: Boolean) {
        dialogState = it
    }
}