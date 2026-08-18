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

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            HomeScreen9(
                onStartRecording = {
                    navController.navigate("recording")
                }
            )
        }
        composable("recording"){
            val viewModel: RecordingViewModel = hiltViewModel()
//            RecordingScreen(
//                onRecordingFinish = {
//                    navController.navigate("result/10086")
//                }
//            )
        }
        composable("result/{audioId}"){
            val audioId = it.arguments?.getString("audioId")
            ResultScreen(audioId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}