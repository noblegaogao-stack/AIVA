package com.noble.aiva.di

import android.content.Context
import com.noble.aiva.data.repository.AndroidAudioRecorder
import com.noble.aiva.data.repository.AudioRepository
import com.noble.aiva.data.repository.AudioRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * @InstallIn 这个Module 应该属于哪个Hilt Component
 *          也就是这个生命周期归谁管理
*/
@Module
@InstallIn(SingletonComponent::class)
object AudioModule {

    // 这个对象不方面直接通过构造函数注入，所以我提供一个创建方法。
    // @Provides , 这个方法负责提供一个依赖对象
    @Provides
    @Singleton
    fun provideAudioRecorder(@ApplicationContext context: Context): AndroidAudioRecorder {

        return AndroidAudioRecorder(context, CoroutineScope(SupervisorJob() + Dispatchers.IO))
    }

    @Provides
    @Singleton
    fun provideAudioRepository(audioRecorder: AndroidAudioRecorder): AudioRepository {
        return AudioRepositoryImpl(audioRecorder)
    }
}