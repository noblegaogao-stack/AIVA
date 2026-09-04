package com.noble.aiva.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.noble.aiva.data.local.entity.RecordingEntity
import kotlinx.coroutines.flow.Flow

// Data Access Object, 专门负责和数据库说话
@Dao
interface RecordingDao {
    suspend fun insert(
        recording: RecordingEntity
    ): Long

    /**
     * Flow<List<RecordingEntity>>
     *      数据库发生变化
     *         ↓
     *       Flow自动发射
     *         ↓
     *      ViewModel收到
     *         ↓
     *      StateFlow
     *         ↓
     *      Compose重新绘制
     *
     */
    @Query("SELECT * FROM recordings ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<RecordingEntity>>

    @Query("SELECT * FROM recordings WHERE id = :id")
    fun getById(id: Long): RecordingEntity?

    @Update
    suspend fun update(recording: RecordingEntity)

    @Delete
    suspend fun delete(recording: RecordingEntity)
}