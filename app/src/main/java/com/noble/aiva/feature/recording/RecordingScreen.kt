package com.noble.aiva.feature.recording

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable()
fun RecordingScreen(viewModel: RecordingViewModel,
                    onRecordingFinish: () -> Unit) {

    val uiState by viewModel.uiState.collectAsState()

    // 这里产生audioId

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = if (uiState.isRecording){
                "正在录音"
            } else {
                "未录音"
            }
        )

        Text(
            text = "${uiState.duration} 秒"
        )

        // Recording 正在录音，有一个 录音计时器 时间跳动，一个按钮“停止录音”，点击停止计时器
        Button(onClick = {
            if (uiState.isRecording){
                viewModel.onEvent(
                    RecordingEvent.StopClicked
                )
            } else {
                viewModel.onEvent(
                    RecordingEvent.StartClicked
                )
            }
        }) {
            Text(
                text = if (uiState.isRecording){
                    "停止录音"
                } else {
                    "开始录音"
                }
            )
        }

    }

}