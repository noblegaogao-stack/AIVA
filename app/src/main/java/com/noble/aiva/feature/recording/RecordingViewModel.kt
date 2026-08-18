package com.noble.aiva.feature.recording

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noble.aiva.data.repository.AudioRepository
import com.noble.aiva.domain.usecase.StartRecordingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val startRecordingUseCase: StartRecordingUseCase,
    private val audioRepository: AudioRepository): ViewModel(){

    private val _uiState = MutableStateFlow<RecordingUiState>(RecordingUiState())

    // 对外提供的当前状态
    val uiState: StateFlow<RecordingUiState> = _uiState.asStateFlow()


    fun onEvent(event: RecordingEvent){
        when(event){
            RecordingEvent.StartClicked -> {
                startRecording()
            }

            RecordingEvent.StopClicked -> {
                stopRecording()
            }
        }

//        viewModelScope.launch {
//            // 录音完成
//            _audioState.value = RecordingFinished()
//        }
    }

    private fun startRecording(){
        startRecordingUseCase()
        _uiState.update {
            it.copy(
                isRecording = true,
                errorMessage = null
            )
        }
    }

    private fun stopRecording(){
        audioRepository.stopRecord()
        _uiState.update {
            it.copy(
                isRecording = false
            )
        }
    }

}