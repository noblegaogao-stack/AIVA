package com.noble.aiva.domain.usecase

import com.noble.aiva.data.repository.AudioRepository
import jakarta.inject.Inject

open class StartRecordingUseCase @Inject constructor(private val repository: AudioRepository){

    open operator fun invoke() {
        repository.startRecord()
    }
}