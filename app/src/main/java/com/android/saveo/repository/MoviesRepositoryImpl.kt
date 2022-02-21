package com.android.saveo.repository

import com.android.saveo.models.MoviesModel
import com.android.saveo.repository.local.MoviesDao
import com.android.saveo.repository.local.MoviesLM
import com.android.saveo.repository.remote.MoviesApiClient
import com.android.saveo.repository.remote.MoviesRM
import com.android.saveo.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesApiClient: MoviesApiClient,
    private val moviesLM: MoviesLM,
    private val moviesRM: MoviesRM
) : MoviesRepository{
    override suspend fun getMoviesData(paget:Int,limt:Int): Flow<DataState<List<MoviesModel>>> = flow {
        emit(DataState.ShowLoading)
        try {
            val movies = moviesApiClient.getMovies(paget,limt)
            moviesDao.insertAll(moviesLM.mapFromModelToEntity(moviesRM.mapFromEntityToModel(movies)))
        }catch (e:Exception){
            Timber.e(e)
            emit(DataState.HideLoading)
            emit(DataState.Error(e))
        }finally {
            val moviesCatch = moviesDao.getMoviesData()
            emit(DataState.Success(moviesLM.mapFromEntityToModel(moviesCatch)))
        }
    }
}