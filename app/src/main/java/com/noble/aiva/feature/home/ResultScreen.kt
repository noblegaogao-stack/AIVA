package com.noble.aiva.feature.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ResultScreen(onBack: () -> Unit) {
    Text("ResultScreen")
    Button(onClick = onBack) {
        Text("返回")
    }
}