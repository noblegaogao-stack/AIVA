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


/**
 *  @Module 告诉 Hilt：
 * 这里面有一些“对象怎么创建”的规则。
 * @InstallIn 这些依赖属于整个 App 的生命周期。
 *  App启动
 *    ↓
 * Hilt
 *    ↓
 * Database
 *    ↓
 * 整个App共享
 *
 * DatabaseModule = 对象生产说明书
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Hilt: 自动创建
     * @Probides 告诉 Hilt：如果有人需要 AivaDatabase，你就按照这个方法创建。
     * @Singleton : 不要每次有人需要 Database 就创建一个新的
     *
     * 需要 AivaDatabase
     *        ↓
     * Hilt
     *        ↓
     * 调用 provideAivaDatabase()
     *
     * 第一次需要
     *     ↓
     * 创建 Database
     *     ↓
     * 保存实例
     *     ↓
     * 以后继续使用这个实例
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

    /**
     * 这里没有创建 AivaDatabase ， 直接让 Hilt 传进来。
     *-------------------------------
     * 我要 RecordingDao
     *         ↓
     * 需要 AivaDatabase
     *         ↓
     * 我知道怎么创建 AivaDatabase
     *         ↓
     * 调用 provideAivaDatabase()
     *         ↓
     * database.recordingDao()
     *         ↓
     * 得到 RecordingDao
     *---------------------------
     * 这就是： 依赖注入
     * Hilt
     *  │
     *  ├── 创建 AivaDatabase
     *  │
     *  └── 调用 database.recordingDao()
     *                 │
     *                 ↓
     *           RecordingDao
     *
     */
    @Provides
    fun provideRecordingDao(
        database: AivaDatabase
    ): RecordingDao {
        return database.recordingDao()
    }
}