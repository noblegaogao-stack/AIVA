package com.noble.aiva.feature.result

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.noble.aiva.feature.recording.RecordingViewModel

@Composable
fun ResultScreen(resultViewModel: ResultViewModel,  onBack: () -> Unit) {

    Column() {
        val audioId = resultViewModel.audioId
        Text("ResultScreen : $audioId")
        Button(onClick = onBack) {
            Text("返回")
        }
    }

}