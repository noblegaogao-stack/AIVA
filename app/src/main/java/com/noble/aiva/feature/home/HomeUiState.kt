package com.noble.aiva.feature.home

// HomeUiState 是 UI 所需要的状态模型
data class HomeUiState(
    val welcomeText: String = "Welcome to AIVA",
    val isRecording: Boolean = false
)
