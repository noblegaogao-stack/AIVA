package com.noble.aiva.data.remote

import com.noble.aiva.domain.model.UploadRecordingResponse

import java.io.File
import javax.inject.Inject

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody

class AudioUploadDataSource @Inject constructor(
    private val apiService: AivaApiService
){
    suspend fun upload(
        file: File,
        onProgress: (Int) -> Unit
    ): UploadRecordingResponse{
//        val requestBody = file.asRequestBody("audio.wav".toMediaType())

        val requestBody = ProgressRequestBody(
            file = file,
            contentType = "audio/wav".toMediaType(),
            onProgress  = onProgress)

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