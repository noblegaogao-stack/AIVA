package com.noble.aiva.feature.home


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen9(
    onStartRecording: () -> Unit
){
    Button(onClick = onStartRecording) {
        Text("开始录音")
    }
}