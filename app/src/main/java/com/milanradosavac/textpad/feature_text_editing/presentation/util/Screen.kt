package com.milanradosavac.textpad.feature_text_editing.presentation.util

sealed class Screen(private val route: String, val name: String) {

    object MainScreen: Screen("main_screen", "Pocetak")

    object OnlineSyncManagementScreen: Screen("online_sync_management_screen", "Onlajn Sinhronizacija")

    object AboutScreen: Screen("about_screen", "Informacije")

    open operator fun invoke() = this.route
}
