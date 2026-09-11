package com.noble.aiva.domain.repository

import com.noble.aiva.data.local.entity.RecordingEntity
import com.noble.aiva.data.remote.AsrResponse
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

    fun observeById(
        id: Long
    ): Flow<Recording?>

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

    suspend fun uploadAudioId(
        recordingId: Long,
        audioId: String
    )

    suspend fun startAsr(
        audioId: String
    )

    suspend fun getAsrResult(
        audioId: String
    ): AsrResponse

    suspend fun saveTranscript(
        recordingId: Long,
        transcript: String
    )
}