package com.noble.aiva.feature.home

import androidx.lifecycle.ViewModel
import com.noble.aiva.data.repository.AudioRepository
import com.noble.aiva.domain.usecase.StartRecordingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: AudioRepository) : ViewModel() {
    // MutableStateFlow，可变的状态数据流容器， HomeUIState：自定义界面状态数据类
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState



    fun changeWelcomeText(){
        _uiState.value = _uiState.value.copy(welcomeText = "Welcome to Kotlin")
    }

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.RecordClicked -> {
//               repository.startRecord()
                val useCase = StartRecordingUseCase(repository)
            }
            is HomeEvent.StopRecordClicked -> {
//                repository.stopRecord()
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