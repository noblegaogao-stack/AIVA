package com.noble.aiva.domain

import com.noble.aiva.domain.model.Recording
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
}