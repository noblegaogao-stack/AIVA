package com.noble.aiva.data.repository

import com.noble.aiva.di.AudioRecorder
import jakarta.inject.Inject
import kotlinx.coroutines.delay

class AudioRepository @Inject constructor(private val audioRecorder: AudioRecorder){

    fun startRecord(){
        println("startRecord")
        audioRecorder.startRecord()
    }

    fun stopRecord(){
        println("stopRecord")
        audioRecorder.stopRecord()
    }

    suspend fun uploadAudio(): String {
        delay( 2000L)
        return "audioUrl"
    }
}