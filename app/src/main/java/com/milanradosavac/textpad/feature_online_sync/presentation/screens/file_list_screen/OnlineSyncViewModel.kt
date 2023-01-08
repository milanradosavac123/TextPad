package com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import androidx.compose.runtime.*
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milanradosavac.textpad.core.util.Constants.DEVICE_ID_KEY
import com.milanradosavac.textpad.core.util.Constants.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY
import com.milanradosavac.textpad.core.util.Constants.SERVER_URL_KEY
import com.milanradosavac.textpad.feature_online_sync.domain.model.Device
import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.AddFileRequest
import com.milanradosavac.textpad.feature_online_sync.domain.remote.repos.DeviceRepository
import com.milanradosavac.textpad.feature_online_sync.domain.remote.repos.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.util.*

/**
 * The [OnlineSyncViewModel] for the online sync screens
 * @author Milan Radosavac
 */
class OnlineSyncViewModel : ViewModel() {

    /**
     * Application context
     * @author Milan Radosavac
     */
    val context: Context by inject(Context::class.java)

    /**
     * Device repository used to make the device network requests
     * @author Milan Radosavac
     */
    private val deviceRepository: DeviceRepository by inject(DeviceRepository::class.java)

    /**
     * File repository used to make the device network requests
     * @author Milan Radosavac
     */
    private val fileRepository: FileRepository by inject(FileRepository::class.java)

    /**
     * Shared preferences object used to store data like the server url
     * @author Milan Radosavac
     */
    val sharedPreferences: SharedPreferences by inject(SharedPreferences::class.java)

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
    var serverUrlTextState by mutableStateOf("")
        private set

    /**
     * The state object that holds the server url state
     * @author Milan Radosavac
     */
    var serverUrlState by mutableStateOf(sharedPreferences.getString(SERVER_URL_KEY, ""))
        private set

    /**
     * The state object that holds the state that determines
     * if the device adding progress indicator should be visible
     * @author Milan Radosavac
     */
    var deviceAddingProgressState by mutableStateOf(false)
        private set

    /**
     * The state object that holds the state that determines
     * if the file adding progress indicator(s) should be visible
     * @author Milan Radosavac
     */
    var fileAddingProgressState = mutableStateListOf<Boolean>()
        private set

    /**
     * The state object that holds the state that determines
     * if the file loading progress from the local database indicator should be visible
     * @author Milan Radosavac
     */
    var localDatabaseFileLoadingProgressState by mutableStateOf(false)
        private set

    /**
     * Public-friendly local database file loading progress state setter
     * @author Milan Radosavac
     */
    fun onLocalDatabaseFileLoadingProgressStateChanged(value: Boolean) {
        localDatabaseFileLoadingProgressState = value
    }

    /**
     * Public-friendly local database file adding progress state setter
     * @author Milan Radosavac
     */
    fun onFileAddingProgressStateChanged(changeIndex: Int, value: Boolean) {
         fileAddingProgressState[changeIndex] = value
    }

    /**
     * Public-friendly device adding progress state setter
     * @author Milan Radosavac
     */
    fun onDeviceAddingProgressStateChanged(value: Boolean) {
        deviceAddingProgressState = value;
    }

    /**
     * Fetches a list of all text files on the user's device(in the documents and downloads folder(directly, no subfolders supported))
     * @author Milan Radosavac
     */
    fun listTextFiles() {
        if(sharedPreferences.getInt(READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY, 0) > 0) sharedPreferences.edit {
            putInt(READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY, 0)
        }
        Environment.getExternalStorageDirectory().listFiles()?.forEach externalFilesList@ {
            if(it.name == "Documents" || it.name == "Download") {
                it.listFiles { file -> file.extension == "txt" || file.extension == "TXT" }?.forEach { file ->
                    fileState.add(
                        FileListItem(file, id = "", deviceOfOrigin = sharedPreferences.getString(
                            DEVICE_ID_KEY, "")?: "")
                    )
                    fileAddingProgressState.add(false)
                }
            }
        }
    }

    /**
     * Adds the chosen file to the local database as well as the remote sync api
     * @param fileIndex The index of the chosen file to add
     * @author Milan Radosavac
     */
    fun addFile(fileIndex: Int) {
        val file = fileState[fileIndex].file
        onFileAddingProgressStateChanged(fileIndex, true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = fileRepository.addFile(
                AddFileRequest(
                    file.name,
                    sharedPreferences.getString(DEVICE_ID_KEY, "") ?: "",
                ), file
            )

            fileState[fileIndex] = fileState[fileIndex].copy(
                id = response?.fileId.toString()
            )

            fileRepository.addFile(fileState[fileIndex])
        }.invokeOnCompletion {
            onFileAddingProgressStateChanged(fileIndex, false)
        }
    }

    /**
     * Fetches the file info from the local database
     * @author Milan Radosavac
     */
    fun loadFilesFromLocalDatabase() {
        onLocalDatabaseFileLoadingProgressStateChanged(true)
        viewModelScope.launch(Dispatchers.IO) {
            fileRepository.loadFiles().forEachIndexed { i, item ->
                fileState[i] = fileState[i].copy(
                    isSynced = item.isSynced,
                    id = item.id
                )
            }
        }.invokeOnCompletion {
            onLocalDatabaseFileLoadingProgressStateChanged(false)
        }
    }

    /**
     * Save server url event handler
     * @author Milan Radosavac
     */
    fun saveServerUrl() {
        sharedPreferences.edit().putString(SERVER_URL_KEY, serverUrlTextState).apply()
    }

    /**
     * Remove server url event handler
     * @author Milan Radosavac
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
            onDeviceAddingProgressStateChanged(true)
            deviceRepository.addDevice(
                Device(
                    UUID.randomUUID().toString()
                )
            )
        }.invokeOnCompletion {
            onServerUrlStateChanged(sharedPreferences.getString(SERVER_URL_KEY, ""))
            onDeviceAddingProgressStateChanged(false)
        }
    }

    /**
     * The remove device event handler
     * @author Milan Radosavac
     */
    fun removeDevice() {
        viewModelScope.launch {
            deviceRepository.removeDevice(
                sharedPreferences.getString(DEVICE_ID_KEY, "error") ?: "error"
            )
        }
    }

    /**
     * Public-friendly server url state setter
     * @author Milan Radosavac
     */
    fun onServerUrlStateChanged(it: String?) {
        serverUrlState = it
    }

    /**
     * Public-friendly server url text state setter
     * @author Milan Radosavac
     */
    fun onServerUrlTextStateChanged(it: String) {
        serverUrlTextState = it
    }

    /**
     * Public-friendly dialog visibility state setter
     * @author Milan Radosavac
     */
    fun onDialogStateChanged(it: Boolean) {
        dialogState = it
    }
}