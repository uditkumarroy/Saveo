package com.android.saveo.ui.movieslist

import com.android.saveo.utils.DataState

sealed class MoviesStateEvent {
    data class MoviesSuccess(val data: List<MoviesItemView>) : MoviesStateEvent()
    data class Error(val data: DataState.Error) : MoviesStateEvent()
    object ShowProgress : MoviesStateEvent()
    object HideProgress : MoviesStateEvent()
    object Empty : MoviesStateEvent()
}