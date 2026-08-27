package com.noble.aiva.domain.usecase

import android.Manifest
import androidx.annotation.RequiresPermission
import com.noble.aiva.data.repository.AudioRepository
import com.noble.aiva.data.repository.AudioRepositoryImpl
import jakarta.inject.Inject

open class StartRecordingUseCase @Inject constructor(private val repository: AudioRepository){

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    open operator fun invoke() {
        repository.startRecord()
    }
}