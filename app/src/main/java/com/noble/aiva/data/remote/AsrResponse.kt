package com.noble.aiva.data.remote

data class AsrResponse(
    val code:Int,
    val message: String,
    val data: AsrData?
)

data class AsrData(
    val status: String,
    val transcript: String?
)
