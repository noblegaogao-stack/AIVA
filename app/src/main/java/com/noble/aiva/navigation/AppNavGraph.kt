package com.noble.aiva.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noble.aiva.feature.home.HomeScreen9
import com.noble.aiva.ui.recording.RecordingScreen
import com.noble.aiva.ui.recording.RecordingViewModel
import com.noble.aiva.feature.result.ResultScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.noble.aiva.feature.home.HomeViewModel
import com.noble.aiva.feature.result.ResultViewModel

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        /**
         * ========================================================
         * Home
         * ========================================================
         */
        composable(
            route = "home"
        ){
            val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen9(
                homeViewModel,
                onNavigateToRecording = {
                    navController.navigate("recording")
                }
            )
        }

        /**
         * ========================================================
         * Recording
         * ========================================================
         */
        composable(
            route = "recording"
        ){
            val recordingViewModel: RecordingViewModel = hiltViewModel()

            RecordingScreen(
                recordingViewModel,
                onNavigateToResult  = { recordingId ->
                    navController.navigate("result/$recordingId")
                }
            )
        }

        // Result 结束录音，显示录音时长，有一个按钮，回到Home， 有一个按钮，是否上传到服务器/上传AI分析。
        composable(
            route = "result/{recordingId}",
            arguments = listOf(
                navArgument("recordingId"){
                    type = NavType.LongType
                }
            )
        ){ backStackEntry ->
            // val audioId = it.arguments?.getString("audioId")
//            var audioId = backStackEntry.arguments?.getLong("audioId")?:0L

//         audioId 不用管，   应该让 Hilt + Navigation 自动注入。
            val resultViewModel: ResultViewModel = hiltViewModel()
            // 基础传递参数，从 navigation的route 中传递。

            ResultScreen(resultViewModel,
                onBack = {
//                    返回上一层
                    navController.popBackStack()
                }
            )
        }
    }

}