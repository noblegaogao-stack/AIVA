package com.noble.aiva.data.remote

import com.noble.aiva.domain.model.UploadRecordingResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * @Multipart告诉 Retrofit：
 * 这个 HTTP 请求使用 multipart/form-data。
 * Multipart 可以同时携带：
 * 文件
 * 文本参数
 * 其他字段
 * 例如：
 * file = xxx.wav
 * userId = 123
 * language = zh-CN
 * sampleRate = 16000
 *
 * @Part file: MultipartBody.Part
 * 意思是： 把这个 MultipartBody.Part 作为 multipart 请求中的一个 part 上传。
 */
interface AivaApiService {
    @Multipart
    @POST("api/recordings/upload")
    suspend fun uploadRecording(
        @Part file: MultipartBody.Part
    ): UploadRecordingResponse
}