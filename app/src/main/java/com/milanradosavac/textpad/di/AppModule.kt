package com.milanradosavac.textpad.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.milanradosavac.textpad.feature_online_sync.domain.remote.services.DeviceService
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.OnlineSyncViewModel
import com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Application module for dependency injection
 * @author Milan Radosavac
 */
val appModule = module {

    // MainViewModel dependency definition
    single {
        MainViewModel()
    }

    // OnlineSyncViewModel dependency definition
    single {
        OnlineSyncViewModel()
    }

    // SharedPreferences dependency definition
    single<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(androidContext())
    }

    // DeviceService dependency definition
    single {
        DeviceService.create()
    }
}