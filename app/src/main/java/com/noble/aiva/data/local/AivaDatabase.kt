package com.noble.aiva.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noble.aiva.data.local.dao.RecordingDao
import com.noble.aiva.data.local.entity.RecordingEntity

/**
 * Room 数据库的入口
 * 告诉 Room：
 *   我要通过 RecordingDao 操作这个数据库。
 */
@Database(
    entities = [
        RecordingEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AivaDatabase: RoomDatabase() {
    abstract fun recordingDao(): RecordingDao
}