package com.noble.aiva.domain.usecase

import com.noble.aiva.data.repository.AudioRepository
import com.noble.aiva.data.repository.AudioRepositoryImpl
import javax.inject.Inject

class StopRecordingUseCase @Inject constructor(
    private val repository: AudioRepository
){

    suspend operator fun invoke(): String {
        return repository.stopRecord()
    }
}
