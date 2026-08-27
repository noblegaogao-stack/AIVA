package com.noble.aiva.data.repository

interface AudioRepository {
    fun startRecord()
    suspend fun stopRecord(): String
}