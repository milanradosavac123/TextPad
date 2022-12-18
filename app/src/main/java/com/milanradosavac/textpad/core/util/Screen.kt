package com.milanradosavac.textpad.core.util

/**
 * The class that defines what info each [Screen] is supposed to have
 * @param route The screen's route
 * @param name The screen's name
 * @author Milan Radosavac
 */
sealed class Screen(private val route: String, val name: String) {

    /**
     * Main Screen info object
     * @author Milan Radosavac
     */
    object MainScreen: Screen("main_screen", "Pocetak")

    /**
     * Online Sync Screen info object
     * @author Milan Radosavac
     */
    object OnlineSyncManagementScreen: Screen("online_sync_management_screen", "Onlajn Sinhronizacija")

    /**
     * About Screen info object
     * @author Milan Radosavac
     */
    object AboutScreen: Screen("about_screen", "Informacije")

    /**
     * Invoke method that return's the screen's route when the [Screen] is invoked like a method
     * @return The screen's route
     * @author Milan Radosavac
     */
    open operator fun invoke() = this.route
}
