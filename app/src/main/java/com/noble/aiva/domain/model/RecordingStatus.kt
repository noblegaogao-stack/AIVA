package com.noble.aiva.domain.model

enum class RecordingStatus {
    LOCAL,
    UPLOADING,
    UPLOADED,
    PROCESSING,
    COMPLETED,
    UPLOAD_FAILED,
    ASR_FAILED
}