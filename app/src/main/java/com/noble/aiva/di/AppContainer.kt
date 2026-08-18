package com.noble.aiva.di

import com.noble.aiva.data.repository.AudioRecorder
import com.noble.aiva.data.repository.AudioRepository
import com.noble.aiva.domain.usecase.StartRecordingUseCase

// 创建对象，以及组装对象之间的依赖关系
// AppContainer 是对象组装工厂
class AppContainer {
    // 创建 AudioRecorder 对象
    val audioRecorder = AudioRecorder()
    // 创建 AudioRepository 对象，并注入 AudioRecorder
    val audioRepository: AudioRepository = AudioRepository(audioRecorder)
    val startRecordingUseCase = StartRecordingUseCase(audioRepository)
}