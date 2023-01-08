package com.milanradosavac.textpad.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.milanradosavac.textpad.core.util.Constants.DATABASE_NAME
import com.milanradosavac.textpad.feature_online_sync.data.db.FileDatabase
import com.milanradosavac.textpad.feature_online_sync.domain.remote.repos.DeviceRepository
import com.milanradosavac.textpad.feature_online_sync.domain.remote.repos.FileRepository
import com.milanradosavac.textpad.feature_online_sync.presentation.screens.file_list_screen.OnlineSyncViewModel
import com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen.MainViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Application module for dependency injection
 * @author Milan Radosavac
 */
val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            FileDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single {
        val database: FileDatabase = get(FileDatabase::class)
        database.dao
    }

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

    // DeviceRepository dependency definition
    single {
        DeviceRepository.create()
    }

    // FileRepository dependency definition
    single {
        FileRepository.create()
    }

    // Http Client dependency definition
    single {
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}