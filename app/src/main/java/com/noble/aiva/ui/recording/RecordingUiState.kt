package com.noble.aiva.ui.recording

data class RecordingUiState(
    val isRecording: Boolean = false,
    val duration: Int = 0,
    val errorMessage: String? = null
)