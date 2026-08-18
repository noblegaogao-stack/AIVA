package com.noble.aiva.feature.recording

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noble.aiva.data.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor(private val audioRepository: AudioRepository): ViewModel(){

    private val _audioState = MutableStateFlow<RecordingUiState>(RecordingUiState("RecordingState"))

    // 对外提供的当前状态
    val uiState = _audioState.asStateFlow()

    fun onEvent(event: RecordingEvent){
        viewModelScope.launch {
            _audioState.value =
                RecordingUiState("10086", startRecording = true, finishRecording = false)
            // 录音完成
            RecordingFinished(audioId = 10086)
        }
    }
}