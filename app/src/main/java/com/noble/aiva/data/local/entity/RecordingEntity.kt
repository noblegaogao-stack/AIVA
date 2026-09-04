package com.noble.aiva.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus

@Entity(tableName = "recordings")
data class RecordingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val filePath: String,
    val fileName: String,
    val duration: Long,
    val createdAt: Long,
    val status: String
)

fun RecordingEntity.toDomain(): Recording{
    return Recording(
        id = id,
        filePath = filePath,
        fileName = fileName,
        duration = duration,
        createdAt = createdAt,
        status = RecordingStatus.valueOf(status)
        )
}