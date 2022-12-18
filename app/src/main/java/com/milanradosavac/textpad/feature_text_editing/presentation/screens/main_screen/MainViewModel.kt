package com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.milanradosavac.textpad.feature_text_editing.presentation.util.Constants.DEBUG_TAG
import java.util.Stack

class MainViewModel: ViewModel() {

    var textFieldState by mutableStateOf("")
        private set

    var uriState by mutableStateOf<Uri?>(null)
        private set

    var textChangeSinceLastSaveCount = 0
        private set


    private val undoStack by mutableStateOf(initUndoStack())
    val redoStack by mutableStateOf(Stack<String>())

    fun undo() {
        val string = undoStack.pop()
        redoStack.push(string)
        textFieldState = undoStack.peek()
        textChangeSinceLastSaveCount--
        Log.d(DEBUG_TAG, textChangeSinceLastSaveCount.toString())
    }

    fun redo() {
        val string = redoStack.pop()
        undoStack.push(string)
        textFieldState = string
        textChangeSinceLastSaveCount++
        Log.d(DEBUG_TAG, textChangeSinceLastSaveCount.toString())
    }

    fun clearStacks() {
        undoStack.clear()
        if(redoStack.isNotEmpty()) redoStack.clear()
    }

    private fun initUndoStack(): Stack<String> {
        val stack = Stack<String>()
        stack.push("")
        return stack
    }

    fun resetTextChangeSinceLastSaveCount() {
        textChangeSinceLastSaveCount = 0
    }

    fun onUriStateChanged(it: Uri? = null) {
        uriState = it
    }

    fun onTextFieldStateChanged(it: String) {
        textFieldState = it
        undoStack.push(it)
        textChangeSinceLastSaveCount++
        Log.d(DEBUG_TAG, textChangeSinceLastSaveCount.toString())
    }
}