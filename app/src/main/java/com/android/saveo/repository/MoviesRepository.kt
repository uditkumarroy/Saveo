package com.android.saveo.repository

import com.android.saveo.models.MoviesModel
import com.android.saveo.utils.DataState
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    suspend fun getMoviesData(paget:Int,limt:Int): Flow<DataState<List<MoviesModel>>>
}