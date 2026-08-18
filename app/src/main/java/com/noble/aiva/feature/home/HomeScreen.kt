package com.noble.aiva.feature.home


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState


@Composable
fun HomeScreen9(
    homeViewModel: HomeViewModel,
    onStartRecording: () -> Unit
){
    val uiState = homeViewModel.uiState.collectAsState()

    Column() {
        // TODO ， 初始化的是，recording， 感觉不对 ？
        Text("Home 主界面，显示历史记录 和 录音按钮 : ${uiState.value.welcomeText}, ${uiState.value.isRecording}")

        Button(onClick = onStartRecording) {
            homeViewModel.startRecording()
            Text("开始录音")
        }

        showHistory()
    }
}

@Composable
fun showHistory(){
    Text("录音历史记录")
}


