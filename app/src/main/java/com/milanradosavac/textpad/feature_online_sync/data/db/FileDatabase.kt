package com.milanradosavac.textpad.feature_online_sync.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.milanradosavac.textpad.feature_online_sync.domain.model.dto.FileDto

/**
 * The [FileDatabase] class that describes the database
 * @author Milan Radosavac
 */
@Database(
    entities = [FileDto::class],
    version = 1,
    exportSchema = false
)
abstract class FileDatabase: RoomDatabase() {

    /**
     * [FileDatabase] data access object instance
     * @author Milan Radosavac
     */
    abstract val dao: FileDao
}
