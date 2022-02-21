package com.android.saveo.di

import com.android.saveo.repository.MoviesRepository
import com.android.saveo.repository.MoviesRepositoryImpl
import com.android.saveo.repository.local.MoviesDao
import com.android.saveo.repository.local.MoviesLM
import com.android.saveo.repository.remote.MoviesApiClient
import com.android.saveo.repository.remote.MoviesRM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Dependency for all the repository
 * Add all the repository in this object
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        moviesDao: MoviesDao,
        moviesApiClient: MoviesApiClient,
        moviesLM: MoviesLM,
        moviesRM: MoviesRM
    ): MoviesRepository {
        return MoviesRepositoryImpl(
            moviesDao,
            moviesApiClient,
            moviesLM,
            moviesRM
        )
    }

}
