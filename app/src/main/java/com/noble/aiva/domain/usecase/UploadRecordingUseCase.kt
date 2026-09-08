package com.noble.aiva.domain.usecase

import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus
import com.noble.aiva.domain.repository.RecordingRepository
import javax.inject.Inject


// 实际上就是业务规则
class UploadRecordingUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    suspend operator fun invoke(
        recordingId: Long,
        onProgress: (Int) -> Unit
    ): String {

        // 1. 查询录音
        val recording = repository.getById(recordingId)
            ?: throw IllegalStateException("录音不存在： $recordingId")

        // 2. 修改状态：上传中
        repository.updateStatus(
            recordingId = recordingId,
            status = RecordingStatus.UPLOADING
        )

        return try {
            // 3. 真正执行上传
            val audioId = repository.upload(recording,onProgress)

            // 4. 上传成功
            repository.updateStatus(
                recordingId = recordingId,
                status = RecordingStatus.UPLOADED
            )
            audioId
        } catch (e: Exception) {
            // 5. 上传失败
            repository.updateStatus(
                recordingId = recordingId,
                status = RecordingStatus.FAILED
            )
            throw e
        }
    }

}