package com.noble.aiva.domain.usecase

import com.noble.aiva.domain.RecordingRepository
import com.noble.aiva.domain.model.Recording
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserverRecordingUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    operator fun invoke(): Flow<List<Recording>>{
        return repository.observeAll()
    }
}