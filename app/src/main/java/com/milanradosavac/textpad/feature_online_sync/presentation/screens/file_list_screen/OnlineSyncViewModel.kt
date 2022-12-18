package com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.milanradosavac.textpad.core.util.Constants.SERVER_URL_KEY
import com.milanradosavac.textpad.feature_text_editing.domain.model.FileListItem
import org.koin.java.KoinJavaComponent.inject

/**
 * The [OnlineSyncViewModel] for the online sync screens
 * @author Milan Radosavac
 */
class OnlineSyncViewModel: ViewModel() {

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