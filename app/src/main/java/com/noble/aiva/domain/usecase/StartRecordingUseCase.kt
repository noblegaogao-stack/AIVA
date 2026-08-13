package com.noble.aiva.domain.usecase

import com.noble.aiva.data.repository.AudioRepository
import jakarta.inject.Inject

open class StartRecordingUseCase @Inject constructor(private val repository: AudioRepository){
//    fun execute(){
//        repository.startRecord()
//    }
//     等价的方法
    open operator fun invoke() {
        repository.startRecord()
    }

}