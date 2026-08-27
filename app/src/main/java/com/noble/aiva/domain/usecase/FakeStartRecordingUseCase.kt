package com.noble.aiva.domain.usecase

import com.noble.aiva.data.repository.AudioRepositoryImpl

class FakeStartRecordingUseCase(repository: AudioRepositoryImpl) : StartRecordingUseCase(repository) {
    override fun invoke(){
        // 测试用例，模拟录音开始逻辑
    }
}