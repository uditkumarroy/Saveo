package com.android.saveo.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rowCachedEntity: MoviesLocalEntityItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rowCachedEntity: List<MoviesLocalEntityItem>): List<Long>

    @Query("SELECT * from movies")
    suspend fun getMoviesData(): List<MoviesLocalEntityItem>

    @Query("DELETE FROM movies")
    fun deleteAll()

}