package com.noble.aiva.feature.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.noble.aiva.data.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandler: SavedStateHandle,
    ) : ViewModel() {

        // ViewModel 获取参数的第二种方法，SaveStateHandle
    private val _audioId = savedStateHandler.get<String>("audioId")
    val audioId = _audioId

    // 根据id ，从repository中取出
    fun getRecordResult(){
//        repository.get()
    }
}