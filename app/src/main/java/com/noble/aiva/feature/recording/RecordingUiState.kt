package com.noble.aiva.feature.recording

data class RecordingUiState(
    val isRecording: Boolean = false,
    val duration: Int = 0,
    val errorMessage: String? = null
)