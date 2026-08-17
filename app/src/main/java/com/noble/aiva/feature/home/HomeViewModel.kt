package com.noble.aiva.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noble.aiva.data.repository.AudioRepository
import com.noble.aiva.data.repository.UpdateState
import com.noble.aiva.domain.usecase.StartRecordingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//@HiltViewModel 告诉 Hilt：这是一个由 Hilt 管理的 ViewModel。
//@Inject constructor 告诉 Hilt：创建这个 ViewModel 时，需要通过这个构造函数注入依赖。
@HiltViewModel
class HomeViewModel @Inject constructor(private val audioRepository: AudioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState("waiting"));
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UpdateState>()
    val events = _events.asSharedFlow()

    fun uploadAudio(){

        viewModelScope.launch {
            _uiState.value = HomeUiState("uploading", isUploading = true)
            val result = audioRepository.uploadAudio()
            _uiState.value = HomeUiState("Uploaded: $result", isUploading = false)
            _events.emit(UpdateState.LOADING)
        }
    }
}