package com.noble.aiva.feature.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.noble.aiva.feature.recording.RecordingViewModel

@Composable
fun ResultScreen(resultViewModel: ResultViewModel,  onBack: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val audioId = resultViewModel.audioId
        Text("ResultScreen : $audioId")
        Button(onClick = onBack) {
            Text("返回")
        }
    }

}