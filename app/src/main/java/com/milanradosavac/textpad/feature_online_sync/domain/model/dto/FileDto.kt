package com.milanradosavac.textpad.feature_online_sync.domain.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.milanradosavac.textpad.feature_online_sync.domain.model.*
import java.io.File

/**
 * The [FileDto] data class represents [File]s, or more accurately,
 * what they look like in the database
 * @param path The path to the actual [File] on the user's [Device]
 * @param originalName The original name of the [File] on the user's [Device] filesystem
 * @param deviceOfOrigin The [Device] id of the user's [Device] the [File] came from
 * @param id The [id] string used to describe, differentiate between,
 * as well as refer to, each [File]
 * @author Milan Radosavac
 */
@Entity(tableName = "file")
data class FileDto(

    @ColumnInfo
    val path: String,

    @ColumnInfo
    val originalName: String,

    @ColumnInfo
    val deviceOfOrigin: String,

    @PrimaryKey(autoGenerate = false)
    val id: String
)