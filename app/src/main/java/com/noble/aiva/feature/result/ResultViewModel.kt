package com.noble.aiva.feature.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.noble.aiva.data.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandler: SavedStateHandle,
    private val repository: AudioRepository
    ) : ViewModel() {
    val audioId = savedStateHandler.get<String>("audioId")
}