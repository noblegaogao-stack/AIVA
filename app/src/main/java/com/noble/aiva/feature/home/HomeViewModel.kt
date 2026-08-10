package com.noble.aiva.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    // MutableStateFlow，可变的状态数据流容器， HomeUIState：自定义界面状态数据类
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun changeWelcomeText(){
        _uiState.value = _uiState.value.copy(welcomeText = "Welcome to Kotlin")
    }

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.RecordClicked -> {
                _uiState.value = _uiState.value.copy(isRecording = true)
            }
            is HomeEvent.StopRecordClicked -> {
                _uiState.value = _uiState.value.copy(isRecording = false)
            }
        }
    }


//    ====================================================================
//    计算器的代码
//    private val _count = MutableStateFlow(0) //MutableStateFlow: ViewModel保存状态的一种方式。
//
//    val count: MutableStateFlow<Int>
//        get() = _count
//
//    fun increase() {
//        _count.value++
//    }
}