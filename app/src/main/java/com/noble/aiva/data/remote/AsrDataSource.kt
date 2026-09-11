package com.noble.aiva.data.remote

import javax.inject.Inject

class AsrDataSource @Inject constructor(
    private val apiService: AivaApiService
){

    /**
     * 启动 ASR
     */
    suspend fun startAsr(
        audioId: String
    ): AsrResponse{
        return apiService.startAsr(
            audioId = audioId
        )
    }


    suspend fun getResult(
        audioId: String
    ) : AsrResponse {
        return  apiService.getAsrResult(
            audioId = audioId
        )
    }
}