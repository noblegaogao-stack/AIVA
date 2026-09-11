package com.noble.aiva.data.local

import com.noble.aiva.data.local.entity.RecordingEntity
import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus

fun RecordingEntity.toDomain(): Recording {
    return Recording(
        id = id,

        filePath = filePath,

        fileName = fileName,

        duration = duration,

        createdAt = createdAt,

        status = RecordingStatus.valueOf(status),

        audioId = audioId,

        transcript = transcript
        )
}