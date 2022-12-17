package com.milanradosavac.textpad.di

import com.milanradosavac.textpad.feature_text_editing.presentation.screens.main_screen.MainViewModel
import org.koin.dsl.module

/**
 * Application module for dependency injection
 * @author Milan Radosavac
 */
val appModule = module {

    single {
        MainViewModel()
    }

}