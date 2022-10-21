package com.milanradosavac.textpad

import android.app.Application
import com.milanradosavac.textpad.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TextPad: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidLogger(Level.ERROR)

            androidContext(this@TextPad)

            modules(
                listOf(
                    appModule
                )
            )

        }
    }
}