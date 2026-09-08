package com.noble.aiva.domain.repository

import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus
import kotlinx.coroutines.flow.Flow

interface RecordingRepository {
    suspend fun insert(
        recording: Recording
    ): Long

    fun observeAll(): Flow<List<Recording>>

    suspend fun getById(
        id: Long
    ): Recording?

    suspend fun delete(
        recording: Recording
    )

    suspend fun upload(
        recording: Recording,
        onProgress: (Int) -> Unit
    ): String

    suspend fun updateStatus(
        recordingId: Long,
        status: RecordingStatus
    )
}