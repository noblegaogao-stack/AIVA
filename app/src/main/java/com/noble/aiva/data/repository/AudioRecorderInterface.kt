package com.noble.aiva.data.repository

interface AudioRecorderInterface {

    /**
     * 开始录音
     */
     fun startRecording()

     /**
      * 停止录音
      * @return 录音文件路径
      * @throws Exception
      */
     suspend fun stopRecording(): String
}