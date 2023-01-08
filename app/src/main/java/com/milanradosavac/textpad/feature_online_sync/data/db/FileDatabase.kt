package com.milanradosavac.textpad.feature_online_sync.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.milanradosavac.textpad.feature_online_sync.domain.model.dto.FileDto

@Database(
    entities = [FileDto::class],
    version = 1,
    exportSchema = false
)
abstract class FileDatabase: RoomDatabase() {


    abstract val dao: FileDao
}
