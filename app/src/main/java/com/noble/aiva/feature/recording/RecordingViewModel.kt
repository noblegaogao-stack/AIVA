package com.noble.aiva.feature.recording

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noble.aiva.domain.usecase.ObserverRecordingUseCase
import com.noble.aiva.domain.usecase.SavedRecordingUseCase
import com.noble.aiva.domain.usecase.StartRecordingUseCase
import com.noble.aiva.domain.usecase.StopRecordingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val startRecordingUseCase: StartRecordingUseCase,
    private val stopRecordingUseCase: StopRecordingUseCase,
    private val savedRecordingUseCase: SavedRecordingUseCase,
    observerRecordingUseCase: ObserverRecordingUseCase
    ): ViewModel(){

    private val _uiState = MutableStateFlow<RecordingUiState>(RecordingUiState())

    // 对外提供的当前状态
    val uiState: StateFlow<RecordingUiState> = _uiState.asStateFlow()

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _recordingFile = MutableStateFlow<String?>(null)
    val recordingFile = _recordingFile.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * stateIn 什么意思
     *
     * Room
     *  ↓
     * Flow
     *  ↓
     * StateFlow
     *  ↓
     * Compose
     *
     */
    val recordings = observerRecordingUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
            )

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun onEvent(event: RecordingEvent){
        when(event){
            RecordingEvent.StartClicked -> {
                startRecording()
            }

            RecordingEvent.StopClicked -> {
                stopRecording()
            }
        }
    }

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun startRecording(){
        //这里启动录音，但是没有开启协程，是因为创建AudioRecorder对象的时候，在后台录音Job
        try {
            startRecordingUseCase()
            _errorMessage.value = null
            _isRecording.value = true
        } catch (e: Exception) {
            _errorMessage.value = e.message ?: "录音启动失败"
        }
    }

    fun stopRecording(){
        viewModelScope.launch {
            try {
                val filePath = stopRecordingUseCase()
                val file = File(filePath)
                savedRecordingUseCase(
                    filePath = filePath,
                    fileName = file.name,
                    duration = 0L
                )
                _recordingFile.value = filePath
                _isRecording.value = false
            } catch (e: Exception) {
                _isRecording.value = false
                _errorMessage.value = e.message ?: "录音停止失败"
            }
        }
    }

    fun  onMicrophonePermissionDenied() {
        _errorMessage.value = "Microphone permission denied"
    }
    fun  clearErrorMessage() {
        _errorMessage.value = null
    }
}