package com.milanradosavac.textpad.core.util

import android.os.Build

inline fun <T> sdk30AndUp(onSDK30: () -> T): T? {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        onSDK30()
    } else null
}