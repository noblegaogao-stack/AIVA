package com.noble.aiva.di

import android.content.Context
import androidx.room.Room
import com.noble.aiva.data.local.AivaDatabase
import com.noble.aiva.data.local.dao.RecordingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Hilt: 自动创建
     */
    @Provides
    @Singleton
    fun provideAivaDatabase(
        @ApplicationContext context: Context
    ): AivaDatabase{
        return Room.databaseBuilder(
            context,
            AivaDatabase::class.java,
            "aiva.db"
        ).build()
    }

    fun provideRecordingDao(
        database: AivaDatabase
    ): RecordingDao {
        return database.recordingDao()
    }
}