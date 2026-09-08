package com.noble.aiva.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import jakarta.inject.Inject
import kotlinx.coroutines.delay

class AudioRepositoryImpl @Inject constructor(
    private val androidAudioRecorder: AndroidAudioRecorder): AudioRepository {

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    override fun startRecord(){
        println("startRecord， 打开麦克风")
        androidAudioRecorder.startRecording()
    }

     override suspend fun stopRecord(): String {
         println("stopRecord")
         return androidAudioRecorder.stopRecording()
    }

     suspend fun uploadAudio(): String {
        delay( 2000L)
        return "audioUrl"
    }
}