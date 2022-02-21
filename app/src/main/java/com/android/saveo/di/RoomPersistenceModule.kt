package com.android.saveo.di

import android.content.Context
import androidx.room.Room
import com.android.saveo.repository.local.MoviesDao
import com.android.saveo.repository.local.SaveoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Dependency for Room Dao
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomPersistenceModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): SaveoDatabase {
        return Room.databaseBuilder(
            context,
            SaveoDatabase::class.java,
            SaveoDatabase.DATA_BASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTestDao(saveoDatabase: SaveoDatabase): MoviesDao {
        return saveoDatabase.moviesDao()
    }
}