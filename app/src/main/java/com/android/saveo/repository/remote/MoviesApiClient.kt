package com.android.saveo.repository.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiClient {
    @GET("/list")
    suspend fun getMovies(@Query("page") page:Int, @Query("limit") limit:Int): MoviesRemoteEntity

    @GET("/list")
    suspend fun getMovies(): MoviesRemoteEntity
}