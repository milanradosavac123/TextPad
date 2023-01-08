package com.milanradosavac.textpad.feature_online_sync.data.remote.repos

import android.content.SharedPreferences
import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.milanradosavac.textpad.core.util.Constants
import com.milanradosavac.textpad.core.util.ext.mapToDto
import com.milanradosavac.textpad.feature_online_sync.data.db.FileDao
import com.milanradosavac.textpad.feature_online_sync.domain.model.FileListItem
import com.milanradosavac.textpad.feature_online_sync.domain.model.requests.AddFileRequest
import com.milanradosavac.textpad.feature_online_sync.domain.model.responses.AddFileResponse
import com.milanradosavac.textpad.feature_online_sync.domain.remote.repos.DeviceRepository
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
                url("$BASE_URL${Constants.HttpRoutes.ROUTE_FILE_ADD}")
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

    override suspend fun updateFile(fileListItem: FileListItem) {

        val fileDto = fileListItem.mapToDto()

        fileDto.apply {
            dao.updateFile(
                SimpleSQLiteQuery("UPDATE file SET path=$path, originalName=$originalName, deviceOfOrigin=$deviceOfOrigin")
            )
        }
    }

    override suspend fun removeFile(fileId: String) {
        dao.deleteFile(fileId)
    }
}