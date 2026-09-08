package com.noble.aiva.di

import com.noble.aiva.data.remote.AivaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Hilt 的依赖关系网
 *
 * Hilt
 *  │
 *  ├── AivaDatabase
 *  │      ↓
 *  │   RecordingDao
 *  │      ↓
 *  │   Repository
 *  │
 *  └── Retrofit
 *         ↓
 *    AivaApiService
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    private const val BASE_URL = "https://888.com"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): AivaApiService{
        return retrofit.create(
            AivaApiService::class.java
        )
    }
}