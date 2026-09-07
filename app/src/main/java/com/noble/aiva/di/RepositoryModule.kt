package com.noble.aiva.di

import com.noble.aiva.data.repository.RecordingRepositoryImpl
import com.noble.aiva.domain.repository.RecordingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {

    /**
     * @Provides : 我需要自己写代码创建对象
     * @Binds： 一个接口对应一个实现类
     */
    @Binds
    @Singleton
    abstract fun bindRecordingRepository(
        impl: RecordingRepositoryImpl
    ): RecordingRepository
}