package com.noble.aiva.domain.usecase

import com.noble.aiva.data.repository.AudioRepository

class StartRecordingUseCase (private val repository: AudioRepository){
//    fun execute(){
//        repository.startRecord()
//    }
//     等价的方法
    operator fun invoke() {
        repository.startRecord()
    }

}