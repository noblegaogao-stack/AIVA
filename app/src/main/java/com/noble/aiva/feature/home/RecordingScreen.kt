package com.noble.aiva.feature.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable()
fun RecordingScreen(onRecordingFinish: () -> Unit) {
    Text("RecordingScreen")
    Button(onClick = onRecordingFinish) {
        Text("结束录音")
    }
}