package com.noble.aiva.data.repository

import com.noble.aiva.di.AudioRecorder
import jakarta.inject.Inject

class AudioRepository @Inject constructor(private val audioRecorder: AudioRecorder){

    fun startRecord(){
        println("startRecord")
        audioRecorder.startRecord()
    }

    fun stopRecord(){
        println("stopRecord")
        audioRecorder.stopRecord()
    }
}