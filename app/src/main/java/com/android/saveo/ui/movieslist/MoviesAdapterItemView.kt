package com.android.saveo.ui.movieslist

import com.android.saveo.R
import com.android.saveo.models.MoviesModel
import com.android.saveo.utils.ViewType

data class MoviesAdapterItemView(private val model: MoviesModel) : ViewType<MoviesModel> {

    override fun layoutId(): Int = R.layout.movie_adapter_item

    override fun data(): MoviesModel = model
}