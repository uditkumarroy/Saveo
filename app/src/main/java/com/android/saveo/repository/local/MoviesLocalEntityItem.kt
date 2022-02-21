package com.android.saveo.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MoviesLocalEntityItem(
    val author: String?,
    val download_url: String?,
    val height: Int,
    @PrimaryKey
    val id: String,
    val url: String?,
    val width: Int
)