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
     * The read external storage permission request amount shared preferences key
     * @author Milan Radosavac
     */
    const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_AMOUNT_KEY = "read_external_storage_permission_request_amount"

    /**
     * The database name constant
     * @author Milan Radosavac
     */
    const val DATABASE_NAME = "textpad-db"

    /**
     * The http route constant definition object
     * @author Milan Radosavac
     */
    object HttpRoutes {

        /**
         * The device adding route constant
         * @author Milan Radosavac
         */
        const val ROUTE_DEVICE_ADD = "/api/device/add"

        /**
         * The device removing route constant
         * @author Milan Radosavac
         */
        const val ROUTE_DEVICE_REMOVE = "/api/device/remove"

        /**
         * The file adding route constant
         * @author Milan Radosavac
         */
        const val ROUTE_FILE_ADD = "/api/file/add"

        /**
         * The file removing route constant
         * @author Milan Radosavac
         */
        const val ROUTE_FIlE_REMOVE = "/api/file/remove"

        /**
         * The file sync starting route constant
         * @author Milan Radosavac
         */
        const val ROUTE_FIlE_SYNC_START = "/api/file/sync/start"

        /**
         * The file sync stopping route constant
         * @author Milan Radosavac
         */
        const val ROUTE_FIlE_SYNC_STOP = "/api/file/sync/start"

        /**
         * The file sync route constant
         * @author Milan Radosavac
         */
        const val ROUTE_FIlE_SYNC = "/api/file/sync"

    }
}