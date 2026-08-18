package com.noble.aiva.feature.recording

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable()
fun RecordingScreen(viewModel: RecordingViewModel,
                    onRecordingFinish: () -> Unit) {

    val uiState by viewModel.uiState.collectAsState()

    Column() {
        Text("RecordingScreen")

        Button(onClick = {
            viewModel.onEvent(
                RecordingEvent.StartRecoding
            )
        }
        ) {
            Text("结束录音")
        }
    }

}