package com.noble.aiva.data.local.dao

import android.adservices.adid.AdId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.noble.aiva.data.local.entity.RecordingEntity
import com.noble.aiva.domain.model.Recording
import kotlinx.coroutines.flow.Flow

// Data Access Object, 专门负责和数据库说话
@Dao
interface RecordingDao {
    @Insert
    suspend fun insert(
        recording: RecordingEntity
    ): Long

    /**
     * Flow<List<RecordingEntity>>
     * Flow 表达的是：我不是只想查询一次，我想持续观察这个查询结果。
     *
     *      数据库发生变化
     *      Room发现数据变化
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

    @Query("""
    SELECT * FROM recordings
    WHERE id = :id
    """)
    fun observeById(
        id: Long
    ): Flow<RecordingEntity?>
    @Update
    suspend fun update(recording: RecordingEntity)

    @Query("""
        UPDATE recordings 
        SET status = :status 
        WHERE id = :id
    """)
    suspend fun updateStatus(id: Long, status: String)

    @Query("""
        UPDATE recordings
        SET audioId =:audioId
        WHERE id = :id
    """)
    suspend fun updateAudioId(id: Long, audioId: String)

    @Query("""
        UPDATE recordings
        SET transcript = :transcript,
            status = :status
        WHERE id = :id
    """)
    suspend fun updateTranscript(id: Long, transcript: String, status: String)

    @Delete
    suspend fun delete(recording: RecordingEntity)
}