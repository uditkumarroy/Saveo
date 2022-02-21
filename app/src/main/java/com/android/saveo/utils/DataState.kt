package com.android.saveo.utils

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object ShowLoading : DataState<Nothing>()
    object HideLoading : DataState<Nothing>()
    class Empty<out T> : DataState<T>()
    
}