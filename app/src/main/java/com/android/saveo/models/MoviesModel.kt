package com.android.saveo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesModel(
    val author: String?,
    val download_url: String?,
    val height: Int,
    val id: String,
    val url: String?,
    val width: Int
): Parcelable