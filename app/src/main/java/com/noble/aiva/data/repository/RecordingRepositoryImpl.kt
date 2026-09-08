package com.noble.aiva.data.repository

import com.noble.aiva.data.local.dao.RecordingDao
import com.noble.aiva.data.local.entity.RecordingEntity
import com.noble.aiva.data.local.toDomain
import com.noble.aiva.data.remote.AudioUploadDataSource
import com.noble.aiva.domain.repository.RecordingRepository
import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

/**
 * @inject constructor
 * 如果 Hilt 知道怎么创建 RecordingDao，那么创建 RecordingRepositoryImpl 时，就把 Dao 自动传进来。
 *
 * Hilt
 *  │
 *  ↓
 * RecordingRepositoryImpl
 *  │
 *  └── RecordingDao
 */
class RecordingRepositoryImpl @Inject constructor(
    private val dao: RecordingDao,
    private val audioUploadDataSource: AudioUploadDataSource
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

    override suspend fun upload(recording: Recording, onProgress: (Int) -> Unit): String {
        val file = File(recording.filePath)

        if (!file.exists()){
            throw IllegalStateException(
                "音频文件不存在，${recording.filePath}"
            )
        }
        val response = audioUploadDataSource.upload(file,onProgress)

        if (response.code != 200){
            throw IllegalStateException(response.message)
        }
        return response.data?.audioId?: throw IllegalStateException(
            "服务器没有返回 audioId"
        )
    }

    override suspend fun updateStatus(
        recordingId: Long,
        status: RecordingStatus
    ) {
        dao.updateStatus(id = recordingId, status = status.name)
    }
}