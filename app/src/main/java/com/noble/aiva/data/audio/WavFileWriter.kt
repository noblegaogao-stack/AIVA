package com.noble.aiva.data.audio

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class WavFileWriter {
    fun convertPcmToWav(pcmFilePath: File, wavFilePath: File, sampleRate: Int, numChannels: Int, bitsPerSample: Int) {
        // 读取PCM文件内容
        val pcmDataLength = pcmFilePath.length()
        val byteRate = sampleRate * numChannels * bitsPerSample / 8
        val blockAlign = numChannels * bitsPerSample / 8

        FileInputStream(pcmFilePath).use { pcmInputStream ->
            FileOutputStream(wavFilePath).use { wavOutputStream ->
                writeWavHeader(
                    wavOutputStream,
                    pcmDataLength,
                    sampleRate,
                    numChannels, byteRate, blockAlign, bitsPerSample)

                val buffer = ByteArray(4096)
                while (true){
                    val readSize = pcmInputStream.read(buffer)
                    if (readSize == -1){
                        break
                    }
                    wavOutputStream.write(buffer, 0, readSize)
                }
            }
        }

    }

    private fun writeWavHeader(
        outputStream: FileOutputStream,
        pcmDataLength: Long,
        sampleRate: Int,
        channels: Int,
        byteRate: Int,
        blockAlign: Int,
        bitsPerSample: Int
    ){
        val totalDataLength = pcmDataLength + 36

        outputStream.write(byteArrayOf('R'.code.toByte(), 'I'.code.toByte(), 'F'.code.toByte(), 'F'.code.toByte()))
        writeIntLittleEndian(outputStream, totalDataLength.toInt())

        outputStream.write(byteArrayOf('W'.code.toByte(), 'A'.code.toByte(), 'V'.code.toByte(), 'E'.code.toByte()))
        outputStream.write(byteArrayOf('f'.code.toByte(), 'm'.code.toByte(), 't'.code.toByte(), ' '.code.toByte()))

        writeIntLittleEndian(outputStream, 16)
        writeShortLittleEndian(outputStream, 1)
        writeShortLittleEndian(outputStream, channels)
        writeIntLittleEndian(outputStream, sampleRate)
        writeIntLittleEndian(outputStream, byteRate)
        writeShortLittleEndian(outputStream, blockAlign)
        writeShortLittleEndian(outputStream, bitsPerSample)

        outputStream.write(byteArrayOf('d'.code.toByte(), 'a'.code.toByte(), 't'.code.toByte(), 'a'.code.toByte()))

        writeIntLittleEndian(outputStream, pcmDataLength.toInt())
    }

    /**
     * WAV Header 的数值字段需要按照 Little Endian 写入
     */
    private fun writeIntLittleEndian(outputStream: FileOutputStream, value: Int) {
        outputStream.write(value and 0xFF)
        outputStream.write(value shr 8 and 0xFF)
        outputStream.write(value shr 16 and 0xFF)
        outputStream.write(value shr 24 and 0xFF)
    }

    private fun writeShortLittleEndian(outputStream: FileOutputStream, value: Int) {
        outputStream.write(value and 0xFF)
        outputStream.write(value shr 8 and 0xFF)
    }
}