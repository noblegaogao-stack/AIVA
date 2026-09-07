package com.noble.aiva.data.network

import com.noble.aiva.domain.model.UploadRecordingResponse

import java.io.File
import javax.inject.Inject

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class AudioUploadDataSource @Inject constructor(
    private val apiService: AivaApiService
){
    suspend fun upload(
        file: File
    ): UploadRecordingResponse{
        val requestBody = file.asRequestBody("audio.wav".toMediaType())

        val multipart = MultipartBody.Part.createFormData(
            name = "file",
            filename = file.name,
            body = requestBody
        )

        return apiService.uploadRecording(
            file = multipart
        )
    }

}