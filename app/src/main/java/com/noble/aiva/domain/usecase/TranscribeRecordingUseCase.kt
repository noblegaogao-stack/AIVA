package com.noble.aiva.domain.usecase

import androidx.room.Insert
import com.noble.aiva.domain.model.RecordingStatus
import com.noble.aiva.domain.repository.RecordingRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class TranscribeRecordingUseCase @Inject constructor(
    private val repository: RecordingRepository
) {

    suspend operator fun invoke(
        recordingId: Long
    ) {

        /**
         * 1. 查询本地录音
         */
        val recording =
            repository.getById(recordingId)
                ?: throw IllegalStateException(
                    "录音不存在"
                )


        /**
         * 2. 必须已经上传成功
         */
        val audioId =
            recording.audioId
                ?: throw IllegalStateException(
                    "录音还没有上传"
                )


        /**
         * 3. 状态：
         *
         * UPLOADED
         *     ↓
         * PROCESSING
         */
        repository.updateStatus(
            recordingId,
            RecordingStatus.PROCESSING
        )


        try {

            /**
             * 4. 通知服务器开始 ASR
             */
            repository.startAsr(
                audioId
            )


            /**
             * 5. 开始轮询
             */
            repeat(30) {

                val response =
                    repository.getAsrResult(
                        audioId
                    )

                val data =
                    response.data
                        ?: throw IllegalStateException(
                            "服务器没有返回识别结果"
                        )


                when (data.status) {

                    "PROCESSING" -> {

                        /**
                         * 服务器还没完成
                         *
                         * 等 2 秒
                         */
                        delay(2000)
                    }


                    "COMPLETED" -> {

                        val transcript =
                            data.transcript
                                ?: throw IllegalStateException(
                                    "识别结果为空"
                                )

                        /**
                         * 6. 保存识别结果
                         */
                        repository.saveTranscript(
                            recordingId,
                            transcript
                        )

                        /**
                         * 完成
                         */
                        return
                    }


                    "FAILED" -> {

                        throw IllegalStateException(
                            "ASR 识别失败"
                        )
                    }


                    else -> {

                        throw IllegalStateException(
                            "未知 ASR 状态：${data.status}"
                        )
                    }
                }
            }


            /**
             * 30 次仍然没有完成
             */
            throw IllegalStateException(
                "ASR 处理超时"
            )

        } catch (e: Exception) {

            repository.updateStatus(
                recordingId,
                RecordingStatus.UPLOAD_FAILED
            )

            throw e
        }
    }
}