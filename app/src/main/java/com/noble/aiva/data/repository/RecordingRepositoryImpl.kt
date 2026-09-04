package com.noble.aiva.data.repository

import com.noble.aiva.data.local.dao.RecordingDao
import com.noble.aiva.data.local.entity.RecordingEntity
import com.noble.aiva.data.local.entity.toDomain
import com.noble.aiva.domain.RecordingRepository
import com.noble.aiva.domain.model.Recording
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordingRepositoryImpl @Inject constructor(
    private val dao: RecordingDao
) : RecordingRepository {
    override suspend fun insert(recording: Recording): Long {
        return dao.insert(RecordingEntity(
            id = recording.id,
            filePath = recording.filePath,
            fileName = recording.fileName,
            duration = recording.duration,
            createdAt = recording.createdAt,
            status = recording.status.name
        ))
    }

    /**
     * Room
     *
     * Flow<List<Entity>>
     *        ↓
     *      Flow map =    -->  Flow<List<Recording>>
     *        ↓
     * Flow<List<Domain>>  ->  List<RecordingEntity>
     *                                   ↓
     *                             List<Recording>
     */
    override fun observeAll(): Flow<List<Recording>> {
        return dao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Long): Recording? {
       return dao.getById(id)?.toDomain()
    }

    override suspend fun delete(recording: Recording) {
        dao.delete(RecordingEntity(
            id = recording.id,
            filePath = recording.filePath,
            fileName = recording.fileName,
            duration = recording.createdAt,
            createdAt = recording.createdAt,
            status = recording.status.name
            ))
    }
}