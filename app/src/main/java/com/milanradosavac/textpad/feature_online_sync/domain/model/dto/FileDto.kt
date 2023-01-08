package com.milanradosavac.textpad.feature_online_sync.domain.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.milanradosavac.textpad.feature_online_sync.domain.model.*

/**
 * The [FileDto] data class represents [FileDto]s, or more accurately,
 * what they look like in the database
 * @param path The path to the actual [FileDto] on the server filesystem
 * @param originalName The original name of the [FileDto] on the user's [Device] filesystem
 * @param deviceOfOrigin The [Device] id of the user's [Device] the [FileDto] came from
 * @param id The [id] string used to describe, differentiate between,
 * as well as refer to, each [FileDto]
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