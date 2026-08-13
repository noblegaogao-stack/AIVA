package com.noble.aiva.domain.usecase

import com.noble.aiva.data.repository.AudioRepository

class FakeStartRecordingUseCase(repository: AudioRepository) : StartRecordingUseCase(repository) {
    override fun invoke(){
        // 测试用例，模拟录音开始逻辑
    }
}