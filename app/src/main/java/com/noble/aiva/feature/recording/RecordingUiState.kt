package com.noble.aiva.feature.recording

data class RecordingUiState(
    val audioId: String = "00000",
    val startRecording: Boolean = false,
    val finishRecording: Boolean = false
)