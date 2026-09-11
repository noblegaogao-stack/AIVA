package com.noble.aiva.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus

@Entity(tableName = "recordings")
data class RecordingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    /**
     * 本地录音文件
     */
    val filePath: String,

    /**
     * 文件名称
     */
    val fileName: String,

    /**
     * 录音时长
     */
    val duration: Long,

    /**
     * 创建时间
     */
    val createdAt: Long,

    /**
     * 业务状态
     */
    val status: String,

    /**
     * 服务端音频 ID
     *
     * 上传成功以后获得
     */
    val audioId: String? = null,

    /**
     * AI 语音识别结果,
     * 是否可以标注识别详情，
     * 男声/女声，中文/英文，语气。急躁还是轻松
     */
    val transcript: String? = null
)
