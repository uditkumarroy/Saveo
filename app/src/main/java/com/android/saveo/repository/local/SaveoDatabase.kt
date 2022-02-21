package com.android.saveo.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * App Room Database class and schema
 * Dao functions
 */
@Database(
    entities = [
        MoviesLocalEntityItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SaveoDatabase : RoomDatabase() {

    companion object {
        const val DATA_BASE_NAME: String = "Saveo_DB"
    }

    //Dao Functions
    abstract fun moviesDao(): MoviesDao

}