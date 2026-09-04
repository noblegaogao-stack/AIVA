package com.noble.aiva.domain.model

data class Recording(
    val id: Long,
    val filePath: String,
    val fileName: String,
    val duration: Long,
    val createdAt: Long,
    val status: RecordingStatus
)
