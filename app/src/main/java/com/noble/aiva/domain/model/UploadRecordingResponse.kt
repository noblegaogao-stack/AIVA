package com.noble.aiva.domain.model

data class UploadRecordingResponse(
    val code: Int,
    val message: String,
    val data: UploadRecordingData?
)

data class UploadRecordingData(
    val audioId: String
)