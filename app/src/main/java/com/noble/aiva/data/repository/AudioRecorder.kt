package com.noble.aiva.data.repository

import jakarta.inject.Inject

class AudioRecorder @Inject constructor() {

    fun startRecord() {
        println("录音机启动，开始录音")
    }

    fun stopRecord() {
        println("关闭录音机，停止录音")
    }
}