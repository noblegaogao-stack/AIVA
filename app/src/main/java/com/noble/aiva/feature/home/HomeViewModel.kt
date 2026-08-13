package com.noble.aiva.feature.home

import androidx.lifecycle.ViewModel
import com.noble.aiva.domain.usecase.StartRecordingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

//@HiltViewModel 告诉 Hilt：这是一个由 Hilt 管理的 ViewModel。
//@Inject constructor 告诉 Hilt：创建这个 ViewModel 时，需要通过这个构造函数注入依赖。
@HiltViewModel
class HomeViewModel @Inject constructor(private val startRecordingUseCase: StartRecordingUseCase) : ViewModel() {
    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.RecordClicked -> {
                startRecordingUseCase()
            }
            is HomeEvent.StopRecordClicked -> {
            }
        }
    }
}