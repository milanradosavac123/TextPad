package com.milanradosavac.textpad.core.util

/**
 * The constant definition object
 * @author Milan Radosavac
 */
object Constants {

    /**
     * The debug log tag constant
     * @author Milan Radosavac
     */
    @Suppress("unused") // suppresses the warning when the debug tag is unused
    const val DEBUG_TAG = "TextPad"

    /**
     * The email tag constant
     * @author Milan Radosavac
     */
    const val EMAIL_TAG = "mail"

    /**
     * The url tag constant
     * @author Milan Radosavac
     */
    const val URL_TAG = "url"

    /**
     * The server url shared preferences key
     * @author Milan Radosavac
     */
    const val SERVER_URL_KEY = "server_url"

    /**
     * The device id shared preferences key
     * @author Milan Radosavac
     */
    const val DEVICE_ID_KEY = "device_id"

    /**
     * The http route constant definition object
     * @author Milan Radosavac
     */
    object HttpRoutes {

        const val ROUTE_DEVICE_ADD = "/api/device/add"

        const val ROUTE_DEVICE_REMOVE = "/api/device/remove"

        const val ROUTE_FILE_ADD = "/api/file/add"

        const val ROUTE_FIlE_REMOVE = "/api/file/remove"

        const val ROUTE_FIlE_SYNC_START = "/api/file/sync/start"

        const val ROUTE_FIlE_SYNC = "/api/file/sync"

    }
}