package com.milanradosavac.textpad.feature_online_sync.domain.remote.repos

import com.milanradosavac.textpad.feature_online_sync.data.db.FileDao
import com.milanradosavac.textpad.feature_online_sync.data.remote.repos.FileRepositoryImpl
import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.AddFileRequest
import com.milanradosavac.textpad.feature_online_sync.domain.model.responses.AddFileResponse
import io.ktor.client.*
import org.koin.java.KoinJavaComponent.inject
import java.io.File

interface FileRepository {

    suspend fun addFile(fileRequest: AddFileRequest, file: File): AddFileResponse?

    suspend fun addFile(fileListItem: FileListItem)

    suspend fun removeFile(fileId: String)

    suspend fun updateFile(fileListItem: FileListItem)

    companion object {

        private val client: HttpClient by inject(HttpClient::class.java)

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