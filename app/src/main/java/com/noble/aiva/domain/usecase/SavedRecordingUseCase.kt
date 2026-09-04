package com.noble.aiva.domain.usecase

import com.noble.aiva.domain.RecordingRepository
import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus
import javax.inject.Inject

// UseCase 负责业务规则。
class SavedRecordingUseCase @Inject constructor(
    private val repository: RecordingRepository
){
    suspend operator fun invoke(
        filePath: String,
        fileName: String,
        duration: Long
    ) : Long {
        val recording = Recording(
            id = 0,
            filePath = filePath,
            fileName = fileName,
            duration = duration,
            createdAt = System.currentTimeMillis(),
            status = RecordingStatus.LOCAL
        )
        return repository.insert(recording)
    }
}