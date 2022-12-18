package com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.milanradosavac.textpad.feature_text_editing.presentation.util.Constants.DEBUG_TAG
import java.util.Stack

/**
 * The [MainViewModel] for the main screen
 * @author Milan Radosavac
 */
class MainViewModel: ViewModel() {

    /**
     * The state object representing the current state of the text field
     * @author Milan Radosavac
     */
    var textFieldState by mutableStateOf("")
        private set

    /**
     * The state object that holds the [Uri] of the opened file
     * @author Milan Radosavac
     */
    var uriState by mutableStateOf<Uri?>(null)
        private set

    /**
     * The change counter since the last time the file was saved
     * @author Milan Radosavac
     */
    var textChangeSinceLastSaveCount = 0
        private set

    /**
     * The stack state object containing the change record since the file was opened/created
     * @author Milan Radosavac
     */
    private val undoStack by mutableStateOf(initUndoStack())

    /**
     * The stack state object containing the undone change record
     * @author Milan Radosavac
     */
    val redoStack by mutableStateOf(Stack<String>())

    /**
     * Undo change event handler
     * @author Milan Radosavac
     */
    fun undo() {
        val string = undoStack.pop()
        redoStack.push(string)
        textFieldState = undoStack.peek()
        if(textChangeSinceLastSaveCount > 0) textChangeSinceLastSaveCount--
        Log.d(DEBUG_TAG, textChangeSinceLastSaveCount.toString())
    }

    /**
     * Redo change event handler
     * @author Milan Radosavac
     */
    fun redo() {
        val string = redoStack.pop()
        undoStack.push(string)
        textFieldState = string
        textChangeSinceLastSaveCount++
    }

    /**
     * Stack clearer
     * @author Milan Radosavac
     */
    fun clearStacks() {
        undoStack.clear()
        if(redoStack.isNotEmpty()) redoStack.clear()
    }

    /**
     * Undo stack initializer
     * @author Milan Radosavac
     */
    private fun initUndoStack(): Stack<String> {
        val stack = Stack<String>()
        stack.push("")
        return stack
    }

    /**
     * Change counter re-setter
     * @author Milan Radosavac
     */
    fun resetTextChangeSinceLastSaveCount() {
        textChangeSinceLastSaveCount = 0
    }

    /**
     * Public-friendly uri state setter
     * @author Milan Radosavac
     */
    fun onUriStateChanged(it: Uri? = null) {
        uriState = it
    }

    /**
     * Public-friendly text field state setter
     * @author Milan Radosavac
     */
    fun onTextFieldStateChanged(it: String) {
        textFieldState = it
        undoStack.push(it)
        textChangeSinceLastSaveCount++
    }
}