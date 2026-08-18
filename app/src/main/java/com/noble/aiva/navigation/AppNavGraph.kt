package com.noble.aiva.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noble.aiva.feature.home.HomeScreen9
import com.noble.aiva.feature.recording.RecordingScreen
import com.noble.aiva.feature.recording.RecordingViewModel
import com.noble.aiva.feature.result.ResultScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.noble.aiva.feature.home.HomeViewModel
import com.noble.aiva.feature.result.ResultViewModel

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        // Home 主要显示历史录音， 有一个按钮，“开始录音”
        composable("home"){
            val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen9(
                homeViewModel,
                onStartRecording = {
                    navController.navigate("recording")
                }
            )
        }
        // Recording 正在录音，有一个 录音计时器 时间跳动，一个按钮“停止录音”
        composable("recording"){
            val recordingViewModel: RecordingViewModel = hiltViewModel()
            val audioId = "10086"

            RecordingScreen(
                recordingViewModel,
                onRecordingFinish = {
                    navController.navigate("result/$audioId")
                }
            )
        }
        // Result 结束录音，显示录音时长，有一个按钮，回到Home， 有一个按钮，是否上传到服务器/上传AI分析。
        composable("result/{audioId}"){
            val resultViewModel: ResultViewModel = hiltViewModel()
            // 基础传递参数，从 navigation的route 中传递。
            val audioId = it.arguments?.getString("audioId")
            ResultScreen(resultViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}