package com.milanradosavac.textpad.core.util

import android.os.Build

/**
 * Sdk version verifier helper lambda
 * @author Milan Radosavac
 */
inline fun <T> sdk30AndUp(onSDK30: () -> T): T? {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        onSDK30()
    } else null
}