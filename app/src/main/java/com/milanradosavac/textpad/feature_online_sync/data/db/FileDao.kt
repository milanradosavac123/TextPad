package com.milanradosavac.textpad.feature_online_sync.data.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.milanradosavac.textpad.feature_online_sync.domain.model.dto.FileDto

@Dao
interface FileDao {

    @Query("SELECT * FROM file")
    fun loadFiles(): List<FileDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFile(fileDto: FileDto)

    @RawQuery
    fun updateFile(fileUpdateQuery: SupportSQLiteQuery): Boolean

    @Query("DELETE FROM file WHERE id=:fileId")
    fun deleteFile(fileId: String)
}
