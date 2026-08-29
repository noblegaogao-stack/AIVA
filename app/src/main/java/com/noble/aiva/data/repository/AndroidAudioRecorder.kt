package com.noble.aiva.data.repository

import android.Manifest
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import com.noble.aiva.data.audio.WavFileWriter
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AndroidAudioRecorder @Inject constructor(
    private val context: Context,
    private val scope: CoroutineScope
) : AudioRecorderInterface {

    companion object{
        //每秒采集 44100 次声音数据
        // 采样率
        //    8000Hz: 电话
        //    16000Hz: 语音识别
        //    44100Hz: 音乐播放
        //    48000Hz: 视频

        private val sampleRate = 16000

        //CHANNEL_IN_MONO 单声道，左右变成一个声道
        private const val channelConfig = AudioFormat.CHANNEL_IN_MONO

        // 16位PCM， ENCODING_PCM_16BIT
        private const val audioFormat = AudioFormat.ENCODING_PCM_16BIT

        private const val CHANNELS = 1

        private const val BITS_PER_SAMPLE = 16
    }

    private var audioRecord: AudioRecord ?= null
    private var recordingJob: Job?= null
    // 为什么要加 @Volatile 注解？
    // 因为 isRecording 是一个 Boolean 变量， 它的值在多个线程之间共享，
    // 因此需要使用 @Volatile 注解来确保多个线程之间的修改值共享。
    //    @Volatile 保证一个线程修改后的值能够及时被其他线程看到。
    @Volatile
    private var isRecording = false
    private var outputFile: File? = null

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    override fun startRecording() {
        if (isRecording) {
            return
        }
        //获取系统建议Buffer 大小
        val minBufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            channelConfig,
            audioFormat
        )

        if (minBufferSize == AudioRecord.ERROR || minBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            throw IllegalArgumentException("minBufferSize is invalid")
        }

        val recordingDirectory = File(context.filesDir, "recording")
        if (!recordingDirectory.exists()){
            recordingDirectory.mkdirs()
        }
        val fileName = createFileName() +".wav"
        val file = File(recordingDirectory, fileName)
        outputFile = file

        val recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            minBufferSize) as AudioRecord?

        if (recorder?.state != AudioRecord.STATE_INITIALIZED) {
            recorder?.release()
            throw IllegalArgumentException("AudioRecord is not initialized")
        }
        audioRecord = recorder
        recorder.startRecording()
        isRecording = true
        recordingJob = scope.launch(Dispatchers.IO) {
            // 开始读取录音
            val buffer = ByteArray(minBufferSize)
            FileOutputStream(outputFile).use { outputStream ->
                while (isRecording) {
                    val readBytes = recorder.read(buffer, 0, buffer.size) ?: 0
                    if (readBytes > 0) {
                        outputStream.write(buffer, 0, readBytes)
                    }
                }
            }
        }
    }

    override suspend fun stopRecording(): String {
        if (!isRecording) {
            return outputFile?.absolutePath ?: throw IllegalArgumentException("outputFile is null")
        }
        isRecording = false
        // 停止录音
        audioRecord?.stop()
        //等待录音 Coroutine 真正结束。
        recordingJob?.join()
        // 释放资源
        audioRecord?.release()
        audioRecord = null
        recordingJob = null

        val pcm = outputFile?: throw IllegalArgumentException("pcmFile is null")
        val wavFile = File(pcm.parent, pcm.nameWithoutExtension + ".wav")

        withContext(Dispatchers.IO) {
            WavFileWriter().convertPcmToWav(pcm, wavFile, sampleRate, CHANNELS, BITS_PER_SAMPLE)
        }
        pcm.delete()

        return wavFile.absolutePath
    }

    private fun createFileName(): String {
        val formatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return "recording_${formatter.format(Date())}"
    }
}