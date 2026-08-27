package com.noble.aiva.feature.home

import androidx.lifecycle.ViewModel
import com.noble.aiva.domain.usecase.StartRecordingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

//@HiltViewModel 告诉 Hilt：这是一个由 Hilt 管理的 ViewModel。
//@Inject constructor 告诉 Hilt：创建这个 ViewModel 时，需要通过这个构造函数注入依赖。
@HiltViewModel
class HomeViewModel @Inject constructor(private val startRecordingUseCase: StartRecordingUseCase) : ViewModel() {
    // home ui state, isRecording 是否录音
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState("waiting"))
    // 对外提供的UI，主要由 Composable 实现的UI使用。
    val uiState = _uiState.asStateFlow()

    // 定义的Event 动作，
    private val _events = MutableSharedFlow<HomeEvent>()
    // 对外提供的Event 动作
    val events = _events.asSharedFlow()

    private val _audioRepository = startRecordingUseCase

    val audioRepository = _audioRepository

    fun startRecording(){
        _uiState.value = HomeUiState("recording",true)
    }
}