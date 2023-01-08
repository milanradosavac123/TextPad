package com.milanradosavac.textpad.feature_online_sync.data.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.milanradosavac.textpad.feature_online_sync.domain.model.dto.FileDto

/**
 * [FileDatabase] data access object
 * @author Milan Radosavac
 */
@Dao
interface FileDao {

    /**
     * File loading from database handler
     * @author Milan Radosavac
     */
    @Query("SELECT * FROM file")
    fun loadFiles(): List<FileDto>

    /**
     * File saving to database handler
     * @author Milan Radosavac
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFile(fileDto: FileDto)

    /**
     * File updating in database handler
     * @author Milan Radosavac
     */
    @RawQuery
    fun updateFile(fileUpdateQuery: SupportSQLiteQuery): Boolean

    /**
     * File deleting from database handler
     * @author Milan Radosavac
     */
    @Query("DELETE FROM file WHERE id=:fileId")
    fun deleteFile(fileId: String)
}
