package com.noble.aiva.feature.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.noble.aiva.data.local.entity.RecordingEntity
import com.noble.aiva.domain.model.Recording

import com.noble.aiva.domain.repository.RecordingRepository
import com.noble.aiva.domain.usecase.TranscribeRecordingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandler: SavedStateHandle,
    private val repository: RecordingRepository,
    private val transcribeRecordingUseCase: TranscribeRecordingUseCase
    ) : ViewModel() {

    /**
     * ==========================================================
     * 1. 从 Navigation 获取 recordingId
     * ==========================================================
     *
     * Navigation:
     *
     * result/{recordingId}
     *
     * 例如：
     *
     * result/15
     *
     * 那么：
     *
     * savedStateHandle["recordingId"]
     *
     * 得到：
     *
     * 15L
     */
    private val recordingId: Long = savedStateHandler["recordingId"] ?: 0L

    /**
     * ==========================================================
     * 2. 从 Room 观察当前录音
     * ==========================================================
     */
    val recording: StateFlow<Recording?>
        get() = repository
            .observeById(id = recordingId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )


    /**
     * ==========================================================
     * 3. 自动启动 ASR
     * ==========================================================
     */
    private var hasStartedAsr = false

    fun startTranscription() {
        /**
         * 防止重复启动
         */
        if (hasStartedAsr) {
            return
        }

        hasStartedAsr = true
        viewModelScope.launch {

            try {

                transcribeRecordingUseCase(
                    recordingId
                )

            } catch (e: Exception) {

                /**
                 * TranscribeRecordingUseCase
                 * 已经负责修改 FAILED 状态
                 *
                 * 这里暂时不重复处理。
                 */
            }
        }
    }
}