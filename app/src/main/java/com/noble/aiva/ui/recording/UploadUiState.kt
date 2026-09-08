package com.noble.aiva.ui.recording

data class UploadUiState(
    val isUploading: Boolean = false,
    val progress: Int = 0,
    val errorMessage: String? = null
)
