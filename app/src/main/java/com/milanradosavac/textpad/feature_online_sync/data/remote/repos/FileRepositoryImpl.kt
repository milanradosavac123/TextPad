package com.milanradosavac.textpad.feature_online_sync.data.remote.repos

import android.content.SharedPreferences
import android.util.Log
import com.milanradosavac.textpad.feature_online_sync.domain.model.responses.FileSyncResponse
import com.milanradosavac.textpad.core.util.Constants
import com.milanradosavac.textpad.core.util.Constants.HttpRoutes.ROUTE_FILE_ADD
import com.milanradosavac.textpad.core.util.Constants.HttpRoutes.ROUTE_FIlE_REMOVE
import com.milanradosavac.textpad.core.util.Constants.HttpRoutes.ROUTE_FIlE_SYNC_START
import com.milanradosavac.textpad.core.util.Constants.HttpRoutes.ROUTE_FIlE_SYNC_STOP
import com.milanradosavac.textpad.core.util.Constants.HttpRoutes.ROUTE_FIlE_SYNC
import com.milanradosavac.textpad.core.util.ext.mapToDto
import com.milanradosavac.textpad.feature_online_sync.data.db.FileDao
import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.AddFileRequest
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.FileSyncRequest
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.RemoveFileRequest
import com.milanradosavac.textpad.feature_online_sync.domain.model.responses.AddFileResponse
import com.milanradosavac.textpad.feature_online_sync.domain.remote.repos.FileRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent
import java.io.File

/**
 * [FileRepository] implementation to interact with the remote sync api
 * @param client The http client for the actual api interactions
 * @param dao The database access object for interacting with the local database
 * @author Milan Radosavac
 */
class FileRepositoryImpl(
    private val client: HttpClient,
    private val dao: FileDao
) : FileRepository {

    /**
     * Shared preferences object used to store data like the server url
     * @author Milan Radosavac
     */
    private val sharedPreferences: SharedPreferences by KoinJavaComponent.inject(SharedPreferences::class.java)

    /**
     * Base url for the api
     * @author Milan Radosavac
     */
    private val BASE_URL = sharedPreferences.getString(Constants.SERVER_URL_KEY, "error") ?: "error"

    override suspend fun addFile(fileRequest: AddFileRequest, file: File): AddFileResponse? {

        return try {
            client.post {
                url("$BASE_URL${ROUTE_FILE_ADD}")
                body = MultiPartFormDataContent(
                    formData {
                        append("file_info", Json.encodeToString(fileRequest))
                        append("file", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "text/plain")
                            append(HttpHeaders.ContentDisposition, "filename=${fileRequest.originalName}")
                        })
                    }
                )
            }
        } catch(e: RedirectResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception1","Error: ${e.response.status.description}")
            null
        } catch(e: ClientRequestException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception2","Error: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception3","Error: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception4","Error: ${e.stackTrace.asList()}")
            null
        }
    }

    override suspend fun addFile(fileListItem: FileListItem) {

        val fileDto = fileListItem.mapToDto()

        dao.saveFile(fileDto)
    }

    override suspend fun loadFiles(): List<FileListItem> {

        val files = dao.loadFiles()

        return files.map {
            FileListItem(
                file = File(it.path),
                isSynced = true,
                deviceOfOrigin = it.deviceOfOrigin,
                id = it.id
            )
        }
    }

    override suspend fun removeFile(fileId: String) {
        try {
            client.delete<Unit> {
                url("$BASE_URL$ROUTE_FIlE_REMOVE")
                contentType(ContentType.Application.Json)
                body = RemoveFileRequest(
                    fileId = fileId
                )
            }
        } catch(e: RedirectResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception1","Error: ${e.response.status.description}")
        } catch(e: ClientRequestException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception2","Error: ${e.response.status.description}")
        } catch(e: ServerResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception3","Error: ${e.response.status.description}")
        } catch(e: Exception) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception4","Error: ${e.message}: ${e.stackTrace.asList()}")
        }
    }

    override suspend fun removeFile(fileListItem: FileListItem) {
        dao.deleteFile(fileListItem.id)
    }

    override suspend fun startSynchronisingFiles(fileSyncRequest: FileSyncRequest) {
        try {
            client.post<Unit> {
                url("$BASE_URL$ROUTE_FIlE_SYNC_START")
                contentType(ContentType.Application.Json)
                body = fileSyncRequest
            }
        } catch(e: RedirectResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception1","Error: ${e.response.status.description}")
        } catch(e: ClientRequestException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception2","Error: ${e.response.status.description}")
        } catch(e: ServerResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception3","Error: ${e.response.status.description}")
        } catch(e: Exception) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception4","Error: ${e.stackTrace.asList()}")
        }
    }

    override suspend fun stopSynchronisingFile(fileSyncRequest: FileSyncRequest) {
        try {
            client.delete<Unit> {
                url("$BASE_URL$ROUTE_FIlE_SYNC_STOP")
                contentType(ContentType.Application.Json)
                body = fileSyncRequest
            }
        } catch(e: RedirectResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception1","Error: ${e.response.status.description}")
        } catch(e: ClientRequestException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception2","Error: ${e.response.status.description}")
        } catch(e: ServerResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception3","Error: ${e.response.status.description}")
        } catch(e: Exception) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception4","Error: ${e.message}:${e.stackTrace.asList()}")
        }
    }

    override suspend fun synchroniseFiles(fileSyncRequest: FileSyncRequest): FileSyncResponse? {
        return try {
            client.get {
                url("$BASE_URL${ROUTE_FIlE_SYNC}")
                contentType(ContentType.Application.Json)
                body = fileSyncRequest
            }
        } catch(e: RedirectResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception1","Error: ${e.response.status.description}")
            null
        } catch(e: ClientRequestException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception2","Error: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception3","Error: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            Log.d("${Constants.DEBUG_TAG}: Server Exception4","Error: ${e.stackTrace.asList()}")
            null
        }
    }
}