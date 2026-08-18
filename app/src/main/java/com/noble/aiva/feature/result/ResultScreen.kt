package com.noble.aiva.feature.result

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ResultScreen(audioId: String?, onBack: () -> Unit) {
    Text("ResultScreen : $audioId")
    Button(onClick = onBack) {
        Text("返回")
    }
}