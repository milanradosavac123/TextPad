package com.milanradosavac.textpad.feature_online_sync.domain.remote.repos

import com.milanradosavac.textpad.feature_online_sync.data.db.FileDao
import com.milanradosavac.textpad.feature_online_sync.data.db.FileDatabase
import com.milanradosavac.textpad.feature_online_sync.data.remote.repos.FileRepositoryImpl
import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.AddFileRequest
import com.milanradosavac.textpad.feature_online_sync.domain.model.responses.AddFileResponse
import io.ktor.client.*
import org.koin.java.KoinJavaComponent.inject
import java.io.File

/**
 * [FileRepository] to interact with the remote sync api
 * @author Milan Radosavac
 */
interface FileRepository {

    /**
     * Adds the file to the remote sync api
     * @author Milan Radosavac
     */
    suspend fun addFile(fileRequest: AddFileRequest, file: File): AddFileResponse?

    /**
     * Adds the device to the local database
     * @author Milan Radosavac
     */
    suspend fun addFile(fileListItem: FileListItem)

    /**
     * Removes the file from the remote sync api
     * @author Milan Radosavac
     */
    suspend fun removeFile(fileId: String)

    /**
     * Updates the file info in the local database
     * @author Milan Radosavac
     */
    suspend fun updateFile(fileListItem: FileListItem)

    companion object {

        /**
         * The http client instance for the actual api interactions
         * @author Milan Radosavac
         */
        private val client: HttpClient by inject(HttpClient::class.java)

        /**
         * [FileDatabase] data access object instance for interacting with the local database
         * @author Milan Radosavac
         */
        private val dao: FileDao by inject(FileDao::class.java)

        /**
         * File Repository generator
         * @return the generated repository
         * @author Milan Radosavac
         */
        fun create(): FileRepository {

            return FileRepositoryImpl(client, dao)
        }
    }
}