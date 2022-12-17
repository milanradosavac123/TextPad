package com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen

import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var textFieldState by mutableStateOf("")
        private set

    var uriState by mutableStateOf<Uri?>(null)
        private set

    var textChangeSinceLastSaveCount = 0
        private set


    fun resetTextChangeSinceLastSaveCount() {
        textChangeSinceLastSaveCount = 0
    }

    fun onUriStateChanged(it: Uri? = null) {
        uriState = it
    }

    fun onTextFieldStateChanged(it: String) {
        textFieldState = it
        textChangeSinceLastSaveCount++
    }
}